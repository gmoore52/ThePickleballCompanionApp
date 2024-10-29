// src/util/alert.js
import { ref } from 'vue';

// types are error, info, success, warning

const alert = ref({
  visible: false,
  type: 'info',
  message: ''
});

// Function to show the alert with type, message, and duration
export const showAlert = (type, message, duration = 5000) => {
  alert.value.type = type;
  alert.value.message = message;
  alert.value.visible = true;

  // Auto-hide after the specified duration
  setTimeout(() => {
    closeAlert();
  }, duration);
};

// Function to close the alert
export const closeAlert = () => {
  alert.value.visible = false;
};

// Export the alert state for use in components
export const useAlert = () => {
  return { alert, showAlert, closeAlert };
};
