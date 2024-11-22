<template>
  <v-container class="big-container d-flex">
    <v-card elevation="10" width="600" class="pa-6">
      <v-card-title class="text-center">Create Pickleball Companion Account</v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="formValid">
          <!-- First Name -->
          <v-text-field
            label="First Name"
            v-model="firstName"
            :rules="[v => !!v || 'First name is required']"
            dense
            :error-messages="firstNameError"
          ></v-text-field>

          <!-- Last Name -->
          <v-text-field
            label="Last Name"
            v-model="lastName"
            :rules="[v => !!v || 'Last name is required']"
            dense
            :error-messages="lastNameError"
          ></v-text-field>

          <!-- Email -->
          <v-text-field
            label="Email"
            v-model="email"
            :rules="[v => !!v || 'Email is required', v => /.+@.+\..+/.test(v) || 'Email must be valid']"
            dense
            :error-messages="emailError"
          ></v-text-field>

          <!-- Skill Level -->
          <v-select
            label="Skill Level"
            v-model="skillLevel"
            :items="skillLevels"
            :rules="[v => !!v || 'Skill level is required']"
            dense
            :error-messages="skillLevelError"
          ></v-select>

          <!-- Username -->
          <v-text-field
            label="Username"
            v-model="username"
            :rules="usernameRules"
            @input="clearUsernameError"
            dense
            :error-messages="usernameError"
          ></v-text-field>

          <!-- Password -->
          <v-text-field
            label="Password"
            v-model="password"
            :rules="passwordRules"
            type="password"
            dense
            :error-messages="passwordError"
          ></v-text-field>

          <!-- Confirm Password -->
          <v-text-field
            label="Confirm Password"
            v-model="confirmPassword"
            :rules="confirmPasswordRules"
            type="password"
            dense
            :error-messages="confirmPasswordError"
          ></v-text-field>
        </v-form>
      </v-card-text>

      <!-- Register Button -->
      <v-card-actions>
        <v-row>
          <v-col>
            <v-btn prepend-icon="mdi-account-plus" color="green" @click="register" :disabled="!formValid">
              Register
            </v-btn>
          </v-col>
          <v-col>
            <v-btn to="/login">
              Already have an account? Click to log in.
            </v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router' // Import useRouter
import {fetchData} from "@/util/fetchData";
import {showAlert} from "@/util/alert";

// Data properties
  const firstName = ref('')
  const lastName = ref('')
  const email = ref('')
  const skillLevel = ref('')
  const username = ref('')
  const password = ref('')
  const confirmPassword = ref('')
  const formValid = ref(false)

  // Error messages
  const firstNameError = ref('')
  const lastNameError = ref('')
  const emailError = ref('')
  const skillLevelError = ref('')
  const usernameError = ref('')
  const passwordError = ref('')
  const confirmPasswordError = ref('')

  // Skill level options
  const skillLevels = ['Beginner', 'Intermediate', 'Advanced', 'Pro']

  // Validation rules
  const usernameRules = [
  v => !!v || 'Username is required',
  v => v.length >= 5 || 'Username must be at least 5 characters'
  ]

  const passwordRules = [
  v => !!v || 'Password is required',
  v => v.length >= 8 || 'Password must be at least 8 characters',
    v => /[A-Z]/.test(v) || 'Password must contain at least one uppercase letter',
    v => /[\d!@#$%^&*(),.?":{}|<>]/.test(v) || 'Password must contain at least one number or special character'
  ]

  const confirmPasswordRules = [
  v => !!v || 'Please confirm your password',
  v => v === password.value || 'Passwords do not match',
    v => v.length >= 8 || 'Password must be at least 8 characters',
    v => /[A-Z]/.test(v) || 'Password must contain at least one uppercase letter',
    v => /[\d!@#$%^&*(),.?":{}|<>]/.test(v) || 'Password must contain at least one number or special character'
  ]

  // Form reference to access validate()
  const form = ref(null)

  // Field validation functions
  const validateFirstName = () => {
  firstNameError.value = firstName.value ? '' : 'First name is required';
}

  const validateLastName = () => {
  lastNameError.value = lastName.value ? '' : 'Last name is required';
}

  const validateEmail = () => {
  emailError.value = email.value ? '' : 'Email is required';
}

  const validateSkillLevel = () => {
  skillLevelError.value = skillLevel.value ? '' : 'Skill level is required';
}

const validateUsername = async () => {
  usernameError.value = username.value ? '' : 'Username is required';
  usernameError.value = await checkUserNameAlreadyExists(username.value) ? 'Username already exists' : '';
}

const checkUserNameAlreadyExists = async (username) => {
  return await fetchData(`/users/check/username?username=${username}`);
};

  const validatePassword = () => {
  passwordError.value = password.value ? '' : 'Password is required';
}

  const validateConfirmPassword = () => {
  confirmPasswordError.value = confirmPassword.value ? '' : 'Please confirm your password';
}

  const resetForm = () => {
  firstName.value = '';
  lastName.value = '';
  email.value = '';
  skillLevel.value = '';
  username.value = '';
  password.value = '';
  confirmPassword.value = '';
  formValid.value = false; // Reset form validation state

  // Clear error messages
  firstNameError.value = '';
  lastNameError.value = '';
  emailError.value = '';
  skillLevelError.value = '';
  usernameError.value = '';
  passwordError.value = '';
  confirmPasswordError.value = '';
}

  // Use router
  const router = useRouter(); // Initialize the router

  // Register button handler
const register = async () => {
  // Validate all fields before checking the form
  validateFirstName();
  validateLastName();
  validateEmail();
  validateSkillLevel();
  await validateUsername();
  validatePassword();
  validateConfirmPassword();

  // Check if any errors exist
  if (
    firstNameError.value ||
    lastNameError.value ||
    emailError.value ||
    skillLevelError.value ||
    usernameError.value ||
    passwordError.value ||
    confirmPasswordError.value
  ) {
    console.log('Form contains errors');
    return; // Stop the form submission if any errors exist
  }

  // Set the form validity after validation
  formValid.value = form.value.validate(); // Assuming form validation rules are set up

  // If the form is valid, proceed with submission
  if (formValid.value) {
    // Build the UserDTO object
    const skillLevelMap = {
      'Beginner': 1,
      'Intermediate': 2,
      'Advanced': 3,
      'Pro': 4
    };

    const userDTO = {
      userName: username.value,
      userFullName: `${firstName.value} ${lastName.value}`, // Combine first and last names
      emailAddress: email.value,
      password: password.value,
      profileImgLoc: null, // Assuming no profile image is provided
      skillLevel: skillLevelMap[skillLevel.value] || null, // Convert skill level string to integer
      accCreationDate: new Date() // Set the current date or any other value as needed
    };

    try {
      // Send the user data to the backend for registration
      const response = await fetchData('/users/add', {
        method: 'POST',
        body: JSON.stringify(userDTO),
        headers: {
          'Content-Type': 'application/json'
        }
      });

      // Show success alert and reset the form
      showAlert('success', 'Your account has been created successfully!', 5000);
      resetForm();

      // Redirect to the login page after account creation
      await router.push('/login');
    } catch (error) {
      console.error('Error adding user:', error);
      showAlert('error', 'There was an error creating your account. Please try again.', 5000);
    }
  } else {
    console.log('Form is not valid');
    showAlert('error', 'Please correct the form errors before submitting.', 5000);
  }
};

const clearUsernameError = () => {
  usernameError.value = '';
};


</script>

<style scoped>
.fill-height {

}
.big-container{
  margin-top: 0px;
  margin-bottom: 0px;
  justify-content: center;
}

.v-card-title {
  font-weight: bold;
  font-size: 24px;
}

.v-btn {
  font-size: 16px;
  font-weight: bold;
}
</style>
