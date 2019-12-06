import {extend} from "vee-validate";
import {alpha_spaces, confirmed, digits, email, min, required} from "vee-validate/dist/rules";

// Add the required rule
extend("required", {
    ...required,
    message(field, values) {
        return `The ${field} is required`;
    }
});

extend("email", {
    ...email,
    message(field, values) {
        return `The ${field} must be a valid email address`;
    }
});

extend("digits", {
    ...digits,
    message(field, values) {
        return `The ${field} must contain four digits`;
    }
});

extend("confirmed", {
    ...confirmed,
    message(field, values) {
        return `The ${field} must match`;
    }
});

extend("alpha_spaces", {
    ...alpha_spaces,
    message(field, values) {
        return `The ${field} must contain a valid name`;
    }
});

extend("min", {
    ...min,
    message(field, values) {
        return `The length of ${field} must 4 or more`;
    }
});
