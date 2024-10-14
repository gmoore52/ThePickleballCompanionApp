<template>
  <v-container class="fill-height d-flex justify-center align-center">
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
            @blur="validateFirstName"
          ></v-text-field>

          <!-- Last Name -->
          <v-text-field
            label="Last Name"
            v-model="lastName"
            :rules="[v => !!v || 'Last name is required']"
            dense
            :error-messages="lastNameError"
            @blur="validateLastName"
          ></v-text-field>

          <!-- Email -->
          <v-text-field
            label="Email"
            v-model="email"
            :rules="[v => !!v || 'Email is required', v => /.+@.+\..+/.test(v) || 'Email must be valid']"
            dense
            :error-messages="emailError"
            @blur="validateEmail"
          ></v-text-field>

          <!-- Skill Level -->
          <v-select
            label="Skill Level"
            v-model="skillLevel"
            :items="skillLevels"
            :rules="[v => !!v || 'Skill level is required']"
            dense
            :error-messages="skillLevelError"
            @blur="validateSkillLevel"
          ></v-select>

          <!-- Username -->
          <v-text-field
            label="Username"
            v-model="username"
            :rules="usernameRules"
            dense
            :error-messages="usernameError"
            @blur="validateUsername"
          ></v-text-field>

          <!-- Password -->
          <v-text-field
            label="Password"
            v-model="password"
            :rules="passwordRules"
            type="password"
            dense
            :error-messages="passwordError"
            @blur="validatePassword"
          ></v-text-field>

          <!-- Confirm Password -->
          <v-text-field
            label="Confirm Password"
            v-model="confirmPassword"
            :rules="confirmPasswordRules"
            type="password"
            dense
            :error-messages="confirmPasswordError"
            @blur="validateConfirmPassword"
          ></v-text-field>
        </v-form>
      </v-card-text>

      <!-- Register Button -->
      <v-card-actions>
        <v-row>
          <v-col>
            <v-btn color="green" @click="register">
              Register
            </v-btn>
          </v-col>
          <v-col>
            <v-btn text to="/login">
              Already have an account? Click to log in.
            </v-btn>
          </v-col>
        </v-row>       
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'

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
  v => v.length >= 8 || 'Password must be at least 8 characters'
]

const confirmPasswordRules = [
  v => !!v || 'Please confirm your password',
  v => v === password.value || 'Passwords do not match'
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

const validateUsername = () => {
  usernameError.value = username.value ? '' : 'Username is required';
}

const validatePassword = () => {
  passwordError.value = password.value ? '' : 'Password is required';
}

const validateConfirmPassword = () => {
  confirmPasswordError.value = confirmPassword.value ? '' : 'Please confirm your password';
}

// Register button handler
const register = () => {
  // Validate all fields before checking the form
  validateFirstName();
  validateLastName();
  validateEmail();
  validateSkillLevel();
  validateUsername();
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
    return; // Skip form validation if there are any errors
  }

  // Validate the form
  if (form.value.validate()) {
    // If form is valid, handle registration logic
    alert('Registration successful!'); // Replace with actual registration logic
  } else {
    console.log('Form is not valid');
  }
}
</script>

<style scoped>
.fill-height {
  min-height: 100vh;
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
