<template>
  <tr class="pointed hover-grey" @click="goToProfile">
    <th scope="row">
      <div class="media align-items-center">
        <a href="#" class="avatar rounded-circle mr-3">
          <img
            alt="Image placeholder"
            src="../../assets/img/theme/team-1-800x800.jpg"
          />
        </a>
        <div class="media-body">
          <span class="mb-0 text-sm">{{ user.fullName }}</span>
        </div>
      </div>
    </th>
    <td>
      <span class="badge badge-dot d-inline-block pt-3">
        <i class="bg-warning"></i> {{ user.userType }}
      </span>
    </td>
    <td>
      <div class="avatar-group">
        <a
          href="#"
          class="avatar avatar-sm"
          data-toggle="tooltip"
          data-original-title="Ryan Tompson"
        >
          <img
            alt="Image placeholder"
            src="../../assets/img/theme/team-1-800x800.jpg"
            class="rounded-circle"
          />
        </a>
        <a
          href="#"
          class="avatar avatar-sm"
          data-toggle="tooltip"
          data-original-title="Romina Hadid"
        >
          <img
            alt="Image placeholder"
            src="../../assets/img/theme/team-2-800x800.jpg"
            class="rounded-circle"
          />
        </a>
        <a
          href="#"
          class="avatar avatar-sm"
          data-toggle="tooltip"
          data-original-title="Alexander Smith"
        >
          <img
            alt="Image placeholder"
            src="../../assets/img/theme/team-3-800x800.jpg"
            class="rounded-circle"
          />
        </a>
        <a
          href="#"
          class="avatar avatar-sm"
          data-toggle="tooltip"
          data-original-title="Jessica Doe"
        >
          <img
            alt="Image placeholder"
            src="../../assets/img/theme/team-4-800x800.jpg"
            class="rounded-circle"
          />
        </a>
      </div>
    </td>
    <td>
      <div class="d-flex align-items-center">
        <span class="">700 points</span>
      </div>
    </td>
    <!-- <td class="text-right">
      <div class="dropdown">
        <a
          class="btn btn-sm btn-icon-only text-light"
          href="#"
          role="button"
          data-toggle="dropdown"
          aria-haspopup="true"
          aria-expanded="false"
        >
          <i class="fas fa-ellipsis-v"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </div>
    </td> -->
  </tr>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  name: "Person",
  methods: {
    goToProfile() {
      this.$router.push({
        name: "user-profile",
        params: { id: this.$hash.encode(this.user.id) }
      });
    }
  }
};
</script>

<style lang="scss" scoped></style>
