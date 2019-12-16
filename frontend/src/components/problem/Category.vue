<template>
    <tr class="pointed hover-grey">
        <th @click="goToProblems" scope="row">
            <div class="media align-items-center">
                <div class="media-body">
                    <span class="mb-0 text-sm">{{ cat.name }}</span>
                </div>
            </div>
        </th>
        <td @click="goToProblems"  class="d-none d-lg-table-cell">
      <span class="badge badge-dot d-inline-block pt-3">
        <i class="bg-warning"></i> {{ cat.problems.length }} problems
      </span>
        </td>
        <td>
            <div class="dropdown">
                <button
                        aria-expanded="false"
                        aria-haspopup="true"
                        class="btn btn-icon-only btn-info"
                        data-toggle="dropdown"
                        role="button"
                >
                    <i class="fa fa-edit"></i>
                </button>
                <div
                        class="dropdown-menu dropdown-menu-right"
                        style="z-index: 2;"
                >
                    <a @click="$emit('edit')" class="dropdown-item">Edit</a>
                    <a @click="$emit('delete')" class="dropdown-item">Delete</a>
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
            submit() {
            },
            goToProblems() {
            }
        }
    };
</script>
