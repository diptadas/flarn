<template>
    <div
            class="d-flex-col justify-content-center align-items-center mt-8"
    >
            <p class="text-muted font-italic text-center">
                No {{ mess }} yet, your {{ mess }} will appear here...
            </p>

        <div class="d-flex justify-content-center align-content-center mt-6">
            <slot></slot>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            mess: {
                type: String
            }
        },
        name: "Empty"
    };
</script>
