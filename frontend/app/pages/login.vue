<script setup lang="ts">
definePageMeta({
  layout: false
})
const { t } = useI18n()
const auth = useAuthStore()
const toast = useToast()

const fields = [{
  name: 'email',
  type: 'email',
  label: t('auth.login.email'),
  placeholder: 'email@exemplo.com',
  required: true
}, {
  name: 'password',
  label: t('auth.login.password'),
  type: 'password',
  placeholder: '********',
  required: true
}]

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
  <div class="min-h-screen flex w-full bg-background font-inter text-foreground">
    <!-- Left Side: Image (Hidden on Mobile) -->
    <div class="hidden lg:flex lg:w-1/2 relative">
      <NuxtImg 
        src="https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=1200&q=80" 
        alt="Luxury Property" 
        class="absolute inset-0 w-full h-full object-cover"
        preload
      />
      <div class="absolute inset-0 bg-black/40 pointer-events-none" />
      
      <!-- Back to Home overlay link -->
      <div class="absolute top-8 left-8 z-10">
        <NuxtLink to="/" class="flex items-center gap-3 text-white hover:opacity-80 transition-opacity">
          <UIcon name="tabler:building" class="size-10"/>
          <span class="text-h4 tracking-tight">Marble & Arch</span>
        </NuxtLink>
      </div>

      <div class="absolute bottom-16 left-16 right-16 z-10 text-white">
        <h2 class="text-h2 mb-4">Bem-vindo de volta ao luxo.</h2>
        <p class="text-body-lg text-white/80 max-w-lg">Descubra as propriedades mais exclusivas e arquiteturas impecáveis selecionadas a dedo para o seu estilo de vida.</p>
      </div>
    </div>

    <!-- Right Side: Form -->
    <div class="w-full lg:w-1/2 flex flex-col justify-center px-8 sm:px-16 md:px-24 xl:px-32 relative bg-card">
      <!-- Mobile Back Button -->
      <NuxtLink to="/" class="lg:hidden absolute top-8 left-8 flex items-center gap-2 text-foreground hover:opacity-80 transition-opacity">
        <UIcon name="i-heroicons-arrow-left" class="size-5"/>
        <span class="text-label-sm">Voltar à Home</span>
      </NuxtLink>

      <div class="w-full max-w-md mx-auto">
        <div class="mb-10 text-center lg:text-left">
          <h1 class="text-h2 text-foreground mb-3">{{ t('auth.login.title') }}</h1>
          <p class="text-body-muted">{{ t('auth.login.subtitle') }}</p>
        </div>

        <UAuthForm
          :fields="fields"
          :validate="validate"
          :submit-button="{ label: t('auth.login.submit'), block: true, size: 'xl' }"
          :on-submit="onSubmit"
        >
          <template #footer>
            <div class="text-center text-sm text-text-dimmed mt-8 border-t border-border pt-6">
              {{ t('auth.login.noAccount') }}
              <ULink to="/signup" class="text-primary font-semibold hover:underline transition-all">
                {{ t('auth.login.signUp') }}
              </ULink>
            </div>
          </template>
        </UAuthForm>
      </div>
    </div>
  </div>
</template>
