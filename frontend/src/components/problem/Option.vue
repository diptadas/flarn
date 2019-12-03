<template>
    <div class="form-group">
        <label>Option {{ id }}</label>
        <input
                :value="value"
                @change="$emit('input', $event.target.value)"
                class="form-control form-control-alternative"
                id="problem-title"
                placeholder="Enter option for Problem"
                type="text"
        />
    </div>
</template>

<script>
    export default {
        props: ["id", "value"],
        name: "Option"
    };
</script>
