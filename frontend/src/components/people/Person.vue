<template>
  <tr class="pointed hover-grey">
    <th scope="row" @click="goToProfile">
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
    <td @click="goToProfile">
      <span class="badge badge-dot d-inline-block pt-3">
        <i class="bg-warning"></i> {{ user.userType }}
      </span>
    </td>
    <td @click="goToProfile">
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
    <td @click="goToProfile">
      <div class="d-flex align-items-center">
        <span class="">700 points</span>
      </div>
    </td>
    <td class="text-right" v-if="action">
      <div class="dropdown">
        <button
          class="btn btn-sm btn-icon-only text-light"
          role="button"
          data-toggle="dropdown"
          aria-haspopup="true"
          aria-expanded="false"
        >
          <i class="fa fa-ellipsis-v"></i>
        </button>
        <div
          class="dropdown-menu dropdown-menu-right dropdown-menu-arrow"
          style="z-index: 2;"
        >
          <a class="dropdown-item" @click="$emit('promote')">Promote User</a>
          <a class="dropdown-item" @click="$emit('delete')">Delete User</a>
        </div>
      </div>
    </td>
  </tr>
</template>

<script>
export default {
  props: {
    action: {
      type: Boolean
    },
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
