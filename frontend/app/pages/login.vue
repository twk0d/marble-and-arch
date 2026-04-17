<script setup lang="ts">
const { t } = useI18n()
const auth = useAuthStore()
const toast = useToast()

const fields = [{
  name: 'email',
  type: 'email',
  label: t('auth.login.email'),
  placeholder: 'email@exemplo.com'
}, {
  name: 'password',
  label: t('auth.login.password'),
  type: 'password',
  placeholder: '********'
}]

const state = reactive({
  email: '',
  password: ''
})

const validate = (state: any) => {
  const errors = []
  if (!state.email) errors.push({ path: 'email', message: 'E-mail é obrigatório' })
  if (!state.password) errors.push({ path: 'password', message: 'Senha é obrigatória' })
  return errors
}

async function onSubmit(event: any) {
  try {
    const credentials = event.data || event
    await auth.login(credentials)
    toast.add({ title: 'Sucesso', description: 'Login realizado com sucesso!', color: 'success' })
    navigateTo('/admin/dashboard')
  } catch (error: any) {
    console.error('Login falhou:', error)
    const errorMsg = error.response?._data?.message || error.message || t('auth.login.error')
    toast.add({ title: 'Erro', description: errorMsg, color: 'error' })
  }
}
</script>

<template>
  <div class="flex items-center justify-center p-4 py-20">
    <UCard class="w-full max-w-md border-neutral-200 dark:border-neutral-800">
      <UAuthForm
        :state="state"
        :fields="fields"
        :validate="validate"
        :title="t('auth.login.title')"
        :description="t('auth.login.subtitle')"
        :submit-button="{ label: t('auth.login.submit'), block: true }"
        :on-submit="onSubmit"
      >
        <template #footer>
          <div class="text-center text-sm text-neutral-500 font-inter">
            {{ t('auth.login.noAccount') }}
            <ULink to="/signup" class="text-primary font-semibold hover:underline">
              {{ t('auth.login.signUp') }}
            </ULink>
          </div>
        </template>
      </UAuthForm>
    </UCard>
  </div>
</template>
