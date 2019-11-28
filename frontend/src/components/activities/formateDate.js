import * as timeago from "timeago.js";

export default {
  filters: {
    formatDate(date) {
      return timeago.format(date);
    }
  }
};
