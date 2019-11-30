<template>
  <div>
    <div class="form-group">
      <label>Question {{ id }}</label>
      <input
        id="problem-title"
        class="form-control form-control-alternative"
        placeholder="Enter question for Problem"
        v-model="content.content"
        @change="$emit('input', content)"
        type="text"
      />
    </div>

    <Option v-for="i in 3" :key="i" :id="i" v-model="value.options[i - 1]" />

    <div class="form-group">
      <label>Answer</label>
      <select
        class="form-control"
        id="exampleFormControlSelect1"
        v-model="content.answer"
      >
        <option selected value="">Select Answer</option>
        <option
          v-for="opt in options"
          :key="opt.id"
          :id="opt.id"
          :value="opt.id"
          >Option {{ opt.id + 1 }}</option
        >
      </select>
    </div>

    <hr class="my-4" />
  </div>
</template>

<script>
import Option from "@/components/problem/Option.vue";
export default {
  props: ["id", "value"],
  name: "Question",
  data() {
    return {
      content: this.value,
      options: [
        {
          id: 0,
          text: "Option 1"
        },
        {
          id: 1,
          text: "Option 2"
        },
        {
          id: 2,
          text: "Option 3"
        }
      ]
    };
  },
  components: {
    Option
  },
  watch: {
    value(oldVal, val) {
      console.log(val, "question");
    }
  }
};
</script>
