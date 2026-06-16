<script setup lang="ts">
import { useToast } from '#imports'

definePageMeta({
  layout: 'admin'
})

const toast = useToast()

const { data: response, pending } = useFetch<any>('/api/v1/admin/users', {
  headers: {
    Authorization: `Bearer ${useCookie('auth_token').value}`
  }
})

const teamMembers = computed(() => {
  if (response.value && response.value.success && response.value.data) {
    return response.value.data.map((u: any) => ({
      id: u.id,
      name: `${u.firstName || ''} ${u.lastName || ''}`.trim(),
      email: u.email,
      role: u.role || 'Usuário',
      status: u.active ? 'active' : 'inactive',
      avatar: 'https://i.pravatar.cc/150?u=' + u.id
    }))
  }
  
  // Fallback while developing
  return [
    {
      id: 'USR-001',
      name: 'Roberto Dantas',
      email: 'roberto@marbleandarch.com',
      role: 'Diretor Geral',
      status: 'active',
      avatar: 'https://i.pravatar.cc/150?u=1'
    }
  ]
})

const columns = [
  { accessorKey: 'user', header: 'Usuário' },
  { accessorKey: 'role', header: 'Cargo / Permissão' },
  { accessorKey: 'status', header: 'Status' },
  { id: 'actions', header: '' }
]

const isInviteModalOpen = ref(false)
const inviteEmail = ref('')
const inviteRole = ref('Corretor')

const roles = ['Diretor', 'Gerente', 'Corretor Sênior', 'Corretor', 'Marketing']

function sendInvite() {
  toast.add({
    title: 'Convite Enviado',
    description: `Um e-mail de acesso foi enviado para ${inviteEmail.value} com o cargo de ${inviteRole.value}.`,
    color: 'success'
  })
  isInviteModalOpen.value = false
  inviteEmail.value = ''
  inviteRole.value = 'Corretor'
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-h2 font-playfair text-foreground">Equipe e Permissões</h1>
        <p class="text-body-base text-text-dimmed mt-1">Gerencie os acessos dos corretores e equipe administrativa.</p>
      </div>
      <UButton color="primary" size="lg" icon="i-heroicons-user-plus" class="shadow-ambient" @click="isInviteModalOpen = true">
        Convidar Membro
      </UButton>
    </div>

    <!-- Team Table -->
    <UCard :ui="{ ring: 'ring-1 ring-border shadow-sm', body: { padding: '' } }">
      <UTable :data="teamMembers" :columns="columns">
        <template #user-cell="{ row }">
          <div class="flex items-center gap-3">
            <UAvatar :src="row.original.avatar" :alt="row.original.name" size="sm" />
            <div>
              <p class="text-sm font-medium text-foreground">{{ row.original.name }}</p>
              <p class="text-xs text-text-dimmed">{{ row.original.email }}</p>
            </div>
          </div>
        </template>
        
        <template #role-cell="{ row }">
          <span class="text-sm text-foreground">{{ row.original.role }}</span>
        </template>

        <template #status-cell="{ row }">
          <UBadge :color="row.original.status === 'active' ? 'success' : 'neutral'" :variant="row.original.status === 'active' ? 'soft' : 'outline'">
            {{ row.original.status === 'active' ? 'Ativo' : 'Inativo' }}
          </UBadge>
        </template>
        
        <template #actions-cell>
          <UButton color="neutral" variant="ghost" icon="i-heroicons-ellipsis-vertical" />
        </template>
      </UTable>
    </UCard>

    <!-- Invite Modal -->
    <UModal v-model:open="isInviteModalOpen" title="Convidar Novo Membro" description="Adicione novos corretores ou administradores à plataforma.">
      <template #body>
        <div class="space-y-4">
          <UFormField label="E-mail do colaborador">
            <UInput v-model="inviteEmail" type="email" placeholder="email@marbleandarch.com" size="lg" class="w-full" />
          </UFormField>
          
          <UFormField label="Nível de Acesso (Cargo)">
            <USelectMenu v-model="inviteRole" :options="roles" size="lg" class="w-full" />
          </UFormField>
          
          <div class="flex justify-end gap-3 mt-6">
            <UButton color="neutral" variant="ghost" @click="isInviteModalOpen = false">Cancelar</UButton>
            <UButton color="primary" variant="solid" :disabled="!inviteEmail" @click="sendInvite">Enviar Convite</UButton>
          </div>
        </div>
      </template>
    </UModal>
  </div>
</template>
