<template>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col col-12 col-lg-8 px-0">
            <div class="col col-12 text-center px-0">
                <button
                        class="btn btn-info"
                        data-target="#modal-category"
                        data-toggle="modal"
                        type="button"
                >
                    Create New Category
                </button>
            </div>

            <div class="col col-12 mt-4 px-0">
                <div class="card shadow">
                    <div class="table-responsive min-h-full" v-if="categories.length">
                        <table class="table align-items-center table-flush">
                            <thead class="thead-light">
                            <tr>
                                <th scope="col">Category Name</th>
                                <!-- <th scope="col"># Problems</th> -->
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <category-item
                                    :cat="cat"
                                    :key="cat.id"
                                    @delete="deleteCat(cat, i)"
                                    @edit="edit(cat, i)"
                                    class="my-4"
                                    v-for="(cat, i) in categories"
                            ></category-item>
                            </tbody>
                        </table>
                    </div>
                    <Empty mess="categories" v-else/>
                </div>
            </div>
        </div>
        </div>

        <div class="col-md-4">
            <div
                    aria-hidden="true"
                    aria-labelledby="modal-category"
                    class="modal fade"
                    id="modal-category"
                    role="dialog"
                    tabindex="-1"
            >
                <div class="modal-dialog modal-dialog-centered modal-" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h6 class="modal-title" id="modal-title-notification">
                                Create new category
                            </h6>
                            <button
                                    aria-label="Close"
                                    class="close"
                                    data-dismiss="modal"
                                    ref="modal-btn-close"
                                    type="button"
                            >
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <div class="py-3">
                                <div class="form-group">
                                    <label class="form-control-label" for="input-country"
                                    >Name</label
                                    >
                                    <input
                                            class="form-control form-control-alternative"
                                            id="input-postal-code"
                                            placeholder="Category Name"
                                            type="text"
                                            v-model="name"
                                    />
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button
                                    :disabled="loading"
                                    @click="createCategory"
                                    class="btn btn-primary"
                                    type="button"
                            >
                <span
                        aria-hidden="true"
                        class="spinner-grow spinner-grow-sm"
                        role="status"
                        v-if="loading"
                ></span>
                                Submit
                            </button>
                            <button
                                    class="btn btn-link  ml-auto"
                                    data-dismiss="modal"
                                    type="button"
                            >
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <button
                    data-target="#modal-category-edit"
                    data-toggle="modal"
                    ref="edit-modal-btn"
                    v-show="false"
            ></button>
            <div
                    aria-hidden="true"
                    aria-labelledby="modal-category-edit"
                    class="modal fade"
                    id="modal-category-edit"
                    role="dialog"
                    tabindex="-1"
            >
                <div class="modal-dialog modal-dialog-centered modal-" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h6 class="modal-title" id="modal-title-notification">
                                Edit category
                            </h6>
                            <button
                                    aria-label="Close"
                                    class="close"
                                    data-dismiss="modal"
                                    ref="modal-btn-close-2"
                                    type="button"
                            >
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <div class="py-3">
                                <div class="form-group">
                                    <label class="form-control-label" for="input-country"
                                    >Name</label
                                    >
                                    <input
                                            class="form-control form-control-alternative"
                                            id="input-postal-code"
                                            placeholder="Category Name"
                                            type="text"
                                            v-model="category.name"
                                    />
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button
                                    :disabled="editloading"
                                    @click="editCat"
                                    class="btn btn-primary"
                                    type="button"
                            >
                <span
                        aria-hidden="true"
                        class="spinner-grow spinner-grow-sm"
                        role="status"
                        v-if="editloading"
                ></span>
                                Submit
                            </button>
                            <button
                                    class="btn btn-link  ml-auto"
                                    data-dismiss="modal"
                                    type="button"
                            >
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <Delete :action="deleteAction" :content="deleteContent" ref="delete"/>
    </div>
</template>

<script>
    import CategoryItem from "@/components/problem/Category.vue";
    import Delete from "@/components/utils/Delete.vue";

    export default {
        name: "ManageCategories",
        data() {
            return {
                loading: false,
                editloading: false,
                categories: [],
                name: "",
                category: {
                    id: "",
                    name: ""
                },
                deleteContent: {},
                deleteAction: null
            };
        },
        methods: {
            createCategory() {
                if (this.loading) return false;
                this.loading = true;
                const url = "category";
                const data = {
                    name: this.name
                };
                this.$http
                    .post(url, data)
                    .then(res => {
                        this.categories.unshift(res.data);
                        this.name = "";
                        this.$refs["modal-btn-close"].click();
                    })
                    .finally(() => (this.loading = false));
            },
            edit(cat, index) {
                this.category = cat;
                this.$refs["edit-modal-btn"].click();
            },
            editCat(id, index) {
                if (this.editloading) return false;
                this.editloading = true;
                const url = `category/update`;
                const data = {
                    id: this.category.id,
                    name: this.category.name
                };
                this.$http
                    .post(url, data)
                    .then(res => {
                        this.categories.splice(index, 1, res.data);
                        this.$refs["modal-btn-close-2"].click();
                    })
                    .finally(() => (this.editloading = false));
            },
            deleteCat(cat, index) {
                this.deleteContent = {
                    name: cat.name
                };
                this.deleteAction = () => this.doDeleteCat(cat, index);
                this.$refs["delete"].show();
            },
            doDeleteCat(cat, index) {
                const url = `category/${cat.id}`;

                this.$http.delete(url).then(res => {
                    this.categories.splice(index, 1);
                });
            },
            getCategories() {
                const url = "category";

                this.$http.get(url).then(res => {
                    this.categories = res.data;
                });
            }
        },
        created() {
            this.getCategories();
        },
        components: {
            CategoryItem,
            Delete
        }
    };
</script>
