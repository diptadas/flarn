<template>
    <div>
        <div class="form-group">
            <label>Question {{ id }}</label>
            <input
                    @change="$emit('input', content)"
                    class="form-control form-control-alternative"
                    id="problem-title"
                    placeholder="Enter question for Problem"
                    type="text"
                    v-model="content.content"
            />
        </div>

        <Option :id="i" :key="i" v-for="i in 3" v-model="value.options[i - 1]"/>

        <div class="form-group">
            <label>Answer</label>
            <select
                    class="form-control"
                    id="exampleFormControlSelect1"
                    v-model="content.answer"
            >
                <option selected value="">Select Answer</option>
                <option
                        :id="opt.id"
                        :key="opt.id"
                        :value="opt.id"
                        v-for="opt in options"
                >Option {{ opt.id + 1 }}
                </option
                >
            </select>
        </div>

        <hr class="my-4"/>
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
