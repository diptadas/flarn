<template>
    <div class="col-md-4">
        <button
                data-target="#modal-delete"
                data-toggle="modal"
                ref="modal-btn-gen"
                v-show="false"
        ></button>
        <div
                aria-hidden="true"
                aria-labelledby="modal-delete"
                class="modal fade"
                id="modal-delete"
                role="dialog"
                tabindex="-1"
        >
            <div
                    class="modal-dialog modal-dialog-centered modal-danger"
                    role="document"
            >
                <div class="modal-content bg-gradient-danger">
                    <div class="modal-header">
                        <h6 class="modal-title" id="modal-title-notification">
                            Your attention is required
                        </h6>
                        <button
                                aria-label="Close"
                                class="close"
                                data-dismiss="modal"
                                ref="modal-btn-close-gen"
                                type="button"
                        >
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="py-3 text-center">
                            <i class="ni ni-bell-55 ni-3x"></i>
                            <h4 class="heading mt-4">
                                Are you sure you want to delete {{ content.name }}
                            </h4>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button
                                :disabled="loading"
                                @click="doAction"
                                class="btn btn-white"
                                type="button"
                        >
              <span
                      aria-hidden="true"
                      class="spinner-grow spinner-grow-sm"
                      role="status"
                      v-if="loading"
              ></span>
                            Confirm
                        </button>
                        <button
                                class="btn btn-link text-white ml-auto"
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
</template>

<script>
    export default {
        name: "Delete",
        props: {
            content: {
                type: Object
            },
            action: {
                type: Function
            }
        },
        data() {
            return {
                loading: false
            };
        },
        methods: {
            show() {
                this.$refs["modal-btn-gen"].click();
            },
            hide() {
                this.$refs["modal-btn-close-gen"].click();
            },
            doAction() {
                this.action();
                this.hide();
            }
        }
    };
</script>
