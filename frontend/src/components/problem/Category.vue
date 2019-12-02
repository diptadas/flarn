<template>
  <tr class="pointed hover-grey">
    <th scope="row" @click="goToProblems">
      <div class="media align-items-center">
        <div class="media-body">
          <span class="mb-0 text-sm">{{ cat.name }}</span>
        </div>
      </div>
    </th>
    <td @click="goToProblems">
      <span class="badge badge-dot d-inline-block pt-3">
        <i class="bg-warning"></i> {{ cat.problems.length }} problems
      </span>
    </td>
    <td class="text-right">
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
          <a class="dropdown-item" @click="$emit('edit')">Edit</a>
          <a class="dropdown-item" @click="$emit('delete')">Delete</a>
        </div>
      </div>
    </td>
  </tr>
</template>

<script>
export default {
  props: {
    cat: {
      type: Object
    }
  },
  name: "Category",
  methods: {
    submit() {},
    goToProblems() {}
  }
};
</script>
