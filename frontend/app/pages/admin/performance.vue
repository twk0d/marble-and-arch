<script setup lang="ts">
definePageMeta({
  layout: 'admin'
})

const brokers = ref([
  { id: 1, name: 'Carolina Mendes', rank: 1, vgv: 'R$ 45.5M', sales: 12, points: 9500, avatar: 'https://i.pravatar.cc/150?u=2', badge: 'Diamante', badgeColor: 'text-sky-500', trend: 'up' },
  { id: 2, name: 'Thiago Silva', rank: 2, vgv: 'R$ 38.2M', sales: 9, points: 8200, avatar: 'https://i.pravatar.cc/150?u=3', badge: 'Ouro', badgeColor: 'text-amber-400', trend: 'up' },
  { id: 3, name: 'Fernanda Costa', rank: 3, vgv: 'R$ 22.0M', sales: 6, points: 5100, avatar: 'https://i.pravatar.cc/150?u=5', badge: 'Prata', badgeColor: 'text-slate-400', trend: 'down' },
  { id: 4, name: 'João Souza', rank: 4, vgv: 'R$ 15.5M', sales: 4, points: 3400, avatar: 'https://i.pravatar.cc/150?u=8', badge: 'Bronze', badgeColor: 'text-orange-700', trend: 'same' }
])

const columns = [
  { accessorKey: 'rank', header: 'Posição' },
  { accessorKey: 'broker', header: 'Corretor' },
  { accessorKey: 'vgv', header: 'VGV (Trimestre)' },
  { accessorKey: 'sales', header: 'Vendas' },
  { accessorKey: 'points', header: 'XP (Pontos)' },
  { accessorKey: 'badge', header: 'Nível' }
]

const userProgress = 82 // percentage to next level
</script>

<template>
  <div class="space-y-8 max-w-6xl">
    <div>
      <h1 class="text-h2 font-playfair text-foreground">Ranking e Metas</h1>
      <p class="text-body-base text-text-dimmed mt-1">Acompanhe a performance do time de vendas e conquistas (Gamificação).</p>
    </div>

    <!-- Gamification Banner -->
    <div class="p-6 rounded-xl bg-gradient-to-r from-primary/20 to-transparent border border-primary/20 flex flex-col md:flex-row items-center gap-6">
      <div class="p-4 bg-background rounded-full shadow-sm border border-border">
        <UIcon name="i-heroicons-trophy" class="w-12 h-12 text-primary" />
      </div>
      <div class="flex-1">
        <h3 class="text-h4 font-playfair text-foreground">Sua Performance: Corretor Ouro</h3>
        <p class="text-sm text-text-dimmed mt-1">Faltam apenas 1.800 XP para você atingir a categoria Diamante!</p>
        <div class="mt-4 flex items-center gap-4">
          <UProgress :model-value="userProgress" color="primary" class="flex-1" />
          <span class="text-sm font-medium text-primary">82%</span>
        </div>
      </div>
      <div>
        <UButton color="primary" variant="solid" size="lg" class="shadow-ambient">Ver Recompensas</UButton>
      </div>
    </div>

    <!-- Leaderboard -->
    <UCard :ui="{ ring: 'ring-1 ring-border shadow-sm', body: { padding: '' } }">
      <template #header>
        <div class="flex justify-between items-center p-4">
          <h3 class="font-medium text-foreground">Leaderboard (Q4 2026)</h3>
          <USelectMenu :options="['Q4 2026', 'Q3 2026', 'Ano 2026']" model-value="Q4 2026" size="xs" class="w-32" />
        </div>
      </template>

      <UTable :data="brokers" :columns="columns">
        <template #rank-cell="{ row }">
          <div class="w-8 h-8 rounded-full flex items-center justify-center font-bold text-sm" :class="row.original.rank === 1 ? 'bg-amber-100 text-amber-700' : (row.original.rank === 2 ? 'bg-slate-100 text-slate-700' : (row.original.rank === 3 ? 'bg-orange-100 text-orange-800' : 'bg-muted text-text-dimmed'))">
            {{ row.original.rank }}º
          </div>
        </template>
        
        <template #broker-cell="{ row }">
          <div class="flex items-center gap-3">
            <UAvatar :src="row.original.avatar" :alt="row.original.name" size="sm" />
            <span class="text-sm font-medium text-foreground">{{ row.original.name }}</span>
          </div>
        </template>
        
        <template #vgv-cell="{ row }">
          <span class="font-semibold text-success">{{ row.original.vgv }}</span>
        </template>
        
        <template #badge-cell="{ row }">
          <div class="flex items-center gap-1.5">
            <UIcon name="i-heroicons-star-solid" :class="['w-4 h-4', row.original.badgeColor]" />
            <span class="text-sm font-medium text-foreground">{{ row.original.badge }}</span>
          </div>
        </template>
      </UTable>
    </UCard>
  </div>
</template>
