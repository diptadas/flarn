<template>
  <div class="col-md-4">
    <button
      data-toggle="modal"
      data-target="#modal-delete"
      ref="modal-btn-gen"
      v-show="false"
    ></button>
    <div
      class="modal fade"
      id="modal-delete"
      tabindex="-1"
      role="dialog"
      aria-labelledby="modal-delete"
      aria-hidden="true"
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
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
              ref="modal-btn-close-gen"
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
            <button type="button" class="btn btn-white" @click="doAction">
              Confirm
            </button>
            <button
              type="button"
              class="btn btn-link text-white ml-auto"
              data-dismiss="modal"
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
