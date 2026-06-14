<template>
  <div class="min-h-screen flex flex-col bg-background font-inter text-foreground">
    <LayoutHeader />
    <main class="flex-1 w-full max-w-[1440px] mx-auto px-4 sm:px-6 lg:px-8 py-8 lg:py-12">
      <slot />
    </main>
    <LayoutFooter />
  </div>
</template>
