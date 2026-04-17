<script setup lang="ts">
const { t } = useI18n()
const toast = useToast()

const fields = [{
  name: 'firstName',
  type: 'text',
  label: t('auth.signup.firstName'),
  placeholder: 'João'
}, {
  name: 'lastName',
  type: 'text',
  label: t('auth.signup.lastName'),
  placeholder: 'Silva'
}, {
  name: 'email',
  type: 'email',
  label: t('auth.signup.email'),
  placeholder: 'email@exemplo.com'
}, {
  name: 'password',
  label: t('auth.signup.password'),
  type: 'password',
  placeholder: '********'
}]

const validate = (state: any) => {
  const errors = []
  if (!state.firstName) errors.push({ path: 'firstName', message: 'Nome é obrigatório' })
  if (!state.lastName) errors.push({ path: 'lastName', message: 'Sobrenome é obrigatório' })
  if (!state.email) errors.push({ path: 'email', message: 'E-mail é obrigatório' })
  if (!state.password) errors.push({ path: 'password', message: 'Senha é obrigatória' })
  return errors
}

async function onSubmit(event: any) {
  try {
    const data = event.data || event
    // The backend expects role as part of the payload, defaulting to BROKER for public signups
    const payload = {
      ...data,
      role: 'BROKER'
    }

    await $fetch('/api/v1/administration/register', {
      method: 'POST',
      body: payload
    })
    
    toast.add({ title: 'Sucesso', description: t('auth.signup.success'), color: 'success' })
    navigateTo('/login')
  } catch (error: any) {
    console.error('Signup error:', error)
    const errorMessage = error.data?.message || error.response?._data?.message || error.message || t('auth.signup.error')
    toast.add({ title: 'Erro', description: errorMessage, color: 'error' })
  }
}
</script>

<template>
  <div class="flex items-center justify-center p-4 py-20">
    <UCard class="w-full max-w-md border-neutral-200 dark:border-neutral-800">
      <UAuthForm
        :fields="fields"
        :validate="validate"
        :title="t('auth.signup.title')"
        :description="t('auth.signup.subtitle')"
        :submit-button="{ label: t('auth.signup.submit'), block: true }"
        :on-submit="onSubmit"
      >
        <template #footer>
          <div class="text-center text-sm text-neutral-500 font-inter">
            {{ t('auth.signup.alreadyHaveAccount') }}
            <ULink to="/login" class="text-primary font-semibold hover:underline">
              {{ t('auth.signup.login') }}
            </ULink>
          </div>
        </template>
      </UAuthForm>
    </UCard>
  </div>
</template>
