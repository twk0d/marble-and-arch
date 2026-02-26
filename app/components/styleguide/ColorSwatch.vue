<script setup lang="ts">
defineProps<{
  colorClass: string;
  name: string;
  description: string;
  hex: string;
  hasBorder?: boolean;
  isText?: boolean;
}>();
</script>

<template>
  <div class="flex items-start gap-4">
    <!-- Square Preview -->
    <div 
      class="w-16 h-16 rounded-md shrink-0 mt-1 flex items-center justify-center overflow-hidden border border-border" 
      :class="isText ? 'bg-card' : colorClass"
    >
      <span v-if="isText" class="text-2xl font-bold font-sora text-foreground">
        Aa
      </span>
    </div>

    <!-- Content -->
    <div class="min-w-0 flex-1">
      <div class="flex flex-wrap items-center gap-x-2 gap-y-0.5">
        <span class="text-body-bold text-base text-foreground">{{ name }}</span>
        <span class="text-body-xs text-neutral-400 font-mono">.{{ colorClass.replace('bg-', '') }}</span>
      </div>
      <div class="text-body-xs font-mono text-foreground/60 mb-1 uppercase">{{ hex }}</div>
      <div class="text-body-sm text-foreground leading-tight">{{ description }}</div>
    </div>
  </div>
</template>
