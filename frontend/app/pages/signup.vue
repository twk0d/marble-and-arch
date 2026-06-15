<script setup lang="ts">
definePageMeta({
  layout: false
})
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

    await $fetch('/api/v1/auth/register', {
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
  <div class="min-h-screen flex w-full bg-background font-inter text-foreground">
    <!-- Left Side: Image (Hidden on Mobile) -->
    <div class="hidden lg:flex lg:w-1/2 relative">
      <NuxtImg 
        src="https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=1200&q=80" 
        alt="Luxury Property Signup" 
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
        <h2 class="text-h2 mb-4">Eleve seus resultados.</h2>
        <p class="text-body-lg text-white/80 max-w-lg">Faça parte do grupo de elite de corretores e parceiros Marble & Arch. Seu próximo grande negócio começa aqui.</p>
      </div>
    </div>

    <!-- Right Side: Form -->
    <div class="w-full lg:w-1/2 flex flex-col justify-center px-8 sm:px-16 md:px-24 xl:px-32 relative bg-card py-16">
      <!-- Mobile Back Button -->
      <NuxtLink to="/" class="lg:hidden absolute top-8 left-8 flex items-center gap-2 text-foreground hover:opacity-80 transition-opacity">
        <UIcon name="i-heroicons-arrow-left" class="size-5"/>
        <span class="text-label-sm">Voltar à Home</span>
      </NuxtLink>

      <div class="w-full max-w-md mx-auto">
        <div class="mb-10 text-center lg:text-left">
          <h1 class="text-h2 text-foreground mb-3">{{ t('auth.signup.title') }}</h1>
          <p class="text-body-muted">{{ t('auth.signup.subtitle') }}</p>
        </div>

        <UAuthForm
          :fields="fields"
          :validate="validate"
          :submit-button="{ label: t('auth.signup.submit'), block: true, size: 'xl' }"
          :on-submit="onSubmit"
        >
          <template #footer>
            <div class="text-center text-sm text-text-dimmed mt-8 border-t border-border pt-6">
              {{ t('auth.signup.alreadyHaveAccount') }}
              <ULink to="/login" class="text-primary font-semibold hover:underline transition-all">
                {{ t('auth.signup.login') }}
              </ULink>
            </div>
          </template>
        </UAuthForm>
      </div>
    </div>
  </div>
</template>
