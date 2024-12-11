<template>
  <v-container class="d-flex justify-center align-center" fill-height>
    <v-card class="pa-6" width="600">
      <v-card-title class="text-h5 login-header">Login</v-card-title>
      <v-card-text>
        <v-form ref="loginForm" v-model="valid">
          <v-text-field
            label="Username"
            v-model="login"
            :rules="[rules.required]"
            clearable
            outlined
          ></v-text-field>

          <v-text-field
            label="Password"
            v-model="password"
            :rules="[rules.required]"
            type="password"
            clearable
            outlined
          ></v-text-field>

          <v-card-actions>
            <v-row>
              <v-col>
                <v-btn prepend-icon="mdi-login"color="green" @click="submitForm">Login</v-btn>
              </v-col>
              <v-col class="text-center">
                <v-btn to="/register">
                  Don't have an account yet? Register here.
                </v-btn>
              </v-col>
            </v-row>
          </v-card-actions>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import {ref} from 'vue';
import {useStore} from 'vuex'; // Import useStore
import {useRouter} from 'vue-router';
import {showAlert} from '@/util/alert.js'; // Import the showAlert utility

const store = useStore(); // Get the Vuex store
const router = useRouter();
const login = ref('');
const password = ref('');
const valid = ref(false);
const loginForm = ref(null);

const rules = {
  required: (value) => !!value || 'Required.',
};

const submitForm = async () => {
  // Validate the form manually when the user clicks 'Login'
  if (loginForm.value.validate()) {
    try {
      await store.dispatch('login', {
        login: login.value,
        password: password.value,
      });
      // Redirect to the homepage after successful login
      await router.push('/');
    } catch (error) {
      // Show the alert if login fails
      showAlert('error', 'Incorrect username or password.', 5000); // Show error alert for 5 seconds
      console.error('Login failed:', error.message);
    }
  } else {
    // console.log('Form is not valid');
  }
};
</script>
<style scoped>
.login-header{
  justify-content: center;
  text-align: center;
}

.v-container {
  max-width: 1168px;
  }
.v-card{
  border-radius: 8px;
}
/* Custom styles if needed */
</style>
