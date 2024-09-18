<template>
  <v-container class="fill-height d-flex justify-center align-center">
    <v-card elevation="10" class="pa-6" width="400">
      <v-card-title class="text-center">Create Pickleball Account</v-card-title>
      <v-card-text>
        <v-form v-model="formValid" ref="form">
          <!-- First Name -->
          <v-text-field
            label="First Name"
            v-model="firstName"
            :rules="[v => !!v || 'First name is required']"
            required
            dense
          ></v-text-field>

          <!-- Last Name -->
          <v-text-field
            label="Last Name"
            v-model="lastName"
            :rules="[v => !!v || 'Last name is required']"
            required
            dense
          ></v-text-field>

          <!-- Email -->
          <v-text-field
            label="Email"
            v-model="email"
            :rules="[v => !!v || 'Email is required', v => /.+@.+\..+/.test(v) || 'Email must be valid']"
            required
            dense
          ></v-text-field>

          <!-- Skill Level -->
          <v-select
            label="Skill Level"
            v-model="skillLevel"
            :items="skillLevels"
            :rules="[v => !!v || 'Skill level is required']"
            required
            dense
          ></v-select>

          <!-- Username -->
          <v-text-field
            label="Username"
            v-model="username"
            :rules="usernameRules"
            required
            dense
          ></v-text-field>

          <!-- Password -->
          <v-text-field
            label="Password"
            v-model="password"
            :rules="passwordRules"
            type="password"
            required
            dense
          ></v-text-field>

          <!-- Confirm Password -->
          <v-text-field
            label="Confirm Password"
            v-model="confirmPassword"
            :rules="confirmPasswordRules"
            type="password"
            required
            dense
          ></v-text-field>
        </v-form>
      </v-card-text>

      <!-- Register Button -->
      <v-card-actions>
        <v-btn color="primary" block @click="register" :disabled="!formValid">
          Register
        </v-btn>
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

// Register button handler
const register = () => {
  if ($refs.form.validate()) {
    alert('Registration successful!'); // Replace with actual registration logic
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
