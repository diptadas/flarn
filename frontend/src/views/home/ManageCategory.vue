<template>
  <div>
    <div class="row col-md-8">
      <div class="col col-12 text-right">
        <button
          type="button"
          class="btn btn-info"
          data-toggle="modal"
          data-target="#modal-category"
        >
          Create New Category
        </button>
      </div>

      <div class="col col-12 mt-4">
        <div class="card shadow">
          <div class="table-responsive min-h-full">
            <table class="table align-items-center table-flush">
              <tbody>
                <category-item
                  v-for="(cat, i) in categories"
                  :key="cat.id"
                  :cat="cat"
                  class="my-4"
                  @edit="edit(cat, i)"
                  @delete="deleteCat(cat, i)"
                ></category-item>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-4">
      <div
        class="modal fade"
        id="modal-category"
        tabindex="-1"
        role="dialog"
        aria-labelledby="modal-category"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-dialog-centered modal-" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h6 class="modal-title" id="modal-title-notification">
                Create new category
              </h6>
              <button
                type="button"
                class="close"
                data-dismiss="modal"
                aria-label="Close"
                ref="modal-btn-close"
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
                    type="text"
                    id="input-postal-code"
                    class="form-control form-control-alternative"
                    placeholder="Category Name"
                    v-model="name"
                  />
                </div>
              </div>
            </div>

            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-primary"
                @click="createCategory"
              >
                Submit
              </button>
              <button
                type="button"
                class="btn btn-link  ml-auto"
                data-dismiss="modal"
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
        data-toggle="modal"
        data-target="#modal-category-edit"
        ref="edit-modal-btn"
        v-show="false"
      ></button>
      <div
        class="modal fade"
        id="modal-category-edit"
        tabindex="-1"
        role="dialog"
        aria-labelledby="modal-category-edit"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-dialog-centered modal-" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h6 class="modal-title" id="modal-title-notification">
                Edit category
              </h6>
              <button
                type="button"
                class="close"
                data-dismiss="modal"
                aria-label="Close"
                ref="modal-btn-close-2"
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
                    type="text"
                    id="input-postal-code"
                    class="form-control form-control-alternative"
                    placeholder="Category Name"
                    v-model="category.name"
                  />
                </div>
              </div>
            </div>

            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click="editCat">
                Submit
              </button>
              <button
                type="button"
                class="btn btn-link  ml-auto"
                data-dismiss="modal"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <Delete ref="delete" :content="deleteContent" :action="deleteAction" />
  </div>
</template>

<script>
import CategoryItem from "@/components/problem/Category.vue";
import Delete from "@/components/utils/Delete.vue";

export default {
  name: "ManageCategories",
  data() {
    return {
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
      const url = "category";
      const data = {
        name: this.name
      };
      this.$http.post(url, data).then(res => {
        this.categories.unshift(res.data);
        this.name = "";
        this.$refs["modal-btn-close"].click();
      });
    },
    edit(cat, index) {
      this.category = cat;
      this.$refs["edit-modal-btn"].click();
    },
    editCat(id, index) {
      const url = `category/update`;
      const data = {
        id: this.category.id,
        name: this.category.name
      };
      this.$http.post(url, data).then(res => {
        this.categories.splice(index, 1, res.data);
        this.$refs["modal-btn-close-2"].click();
      });
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
