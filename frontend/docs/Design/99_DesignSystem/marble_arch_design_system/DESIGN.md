---
name: Marble & Arch Design System
colors:
  surface: '#fcf9f8'
  surface-dim: '#dcd9d9'
  surface-bright: '#fcf9f8'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f6f3f2'
  surface-container: '#f0eded'
  surface-container-high: '#eae7e7'
  surface-container-highest: '#e5e2e1'
  on-surface: '#1c1b1b'
  on-surface-variant: '#4e4639'
  inverse-surface: '#313030'
  inverse-on-surface: '#f3f0ef'
  outline: '#7f7667'
  outline-variant: '#d1c5b4'
  surface-tint: '#775a19'
  primary: '#775a19'
  on-primary: '#ffffff'
  primary-container: '#c5a059'
  on-primary-container: '#4e3700'
  inverse-primary: '#e9c176'
  secondary: '#645d53'
  on-secondary: '#ffffff'
  secondary-container: '#e8ded1'
  on-secondary-container: '#686257'
  tertiary: '#705a49'
  on-tertiary: '#ffffff'
  tertiary-container: '#bba08c'
  on-tertiary-container: '#4a3728'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdea5'
  primary-fixed-dim: '#e9c176'
  on-primary-fixed: '#261900'
  on-primary-fixed-variant: '#5d4201'
  secondary-fixed: '#ebe1d4'
  secondary-fixed-dim: '#cfc5b9'
  on-secondary-fixed: '#1f1b13'
  on-secondary-fixed-variant: '#4c463c'
  tertiary-fixed: '#fbddc7'
  tertiary-fixed-dim: '#dec1ac'
  on-tertiary-fixed: '#28180b'
  on-tertiary-fixed-variant: '#574333'
  background: '#fcf9f8'
  on-background: '#1c1b1b'
  surface-variant: '#e5e2e1'
typography:
  display-lg:
    fontFamily: Playfair Display
    fontSize: 64px
    fontWeight: '700'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Playfair Display
    fontSize: 48px
    fontWeight: '600'
    lineHeight: '1.2'
  headline-lg-mobile:
    fontFamily: Playfair Display
    fontSize: 32px
    fontWeight: '600'
    lineHeight: '1.2'
  headline-md:
    fontFamily: Playfair Display
    fontSize: 32px
    fontWeight: '500'
    lineHeight: '1.3'
  headline-sm:
    fontFamily: Playfair Display
    fontSize: 24px
    fontWeight: '500'
    lineHeight: '1.4'
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '600'
    lineHeight: '1.2'
    letterSpacing: 0.1em
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1.2'
    letterSpacing: 0.05em
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  unit: 8px
  container-max: 1440px
  gutter: 32px
  margin-desktop: 64px
  margin-tablet: 32px
  margin-mobile: 20px
---

## Brand & Style
The design system embodies a "Quiet Luxury" aesthetic, specifically tailored for a high-end real estate platform. It prioritizes a sense of permanence, architectural precision, and exclusivity. The visual direction balances the warmth of Mediterranean masonry with the sharp, clean lines of modern metropolitan penthouses.

The style is a hybrid of **Modern Minimalism** and **Refined Glassmorphism**. It utilizes expansive white space to denote high-value inventory and high-contrast typography to establish authority. The emotional goal is to move the user from a state of searching to a state of "curated discovery," evoking feelings of serenity, trust, and aspiration.

## Colors
The palette is rooted in natural, earthy materials. 
- **Champagne Gold (Primary):** Used for primary calls to action, brand markers, and premium highlights. It is a soft, muted gold—not metallic, but reflective of warm light on stone.
- **Warm Sand (Secondary/Background):** The foundation of the light mode interface. It provides a softer, more luxurious alternative to pure white.
- **Deep Charcoal & Obsidian (Neutrals):** Used for high-contrast typography and deep-mode backgrounds. 
- **Soft Terracotta (Accent):** Reserved for subtle status indicators, secondary buttons, or architectural highlights to add warmth.

**Dark Mode Strategy:** Transitions to a deep Obsidian background. Overlays use a semi-transparent Deep Charcoal to maintain depth, with Gold and Sand used strictly for interactive elements and essential information.

## Typography
Typography is the primary vehicle for the brand’s "Marble & Arch" identity. 
- **Playfair Display** is used for all headlines and high-level editorial content. Its high-contrast strokes reflect classical elegance.
- **Inter** provides a functional, neutral counterpoint for body copy, property specifications, and data-heavy interfaces.

For mobile, headlines scale down aggressively to maintain readability without overwhelming the viewport. Labels use wide letter-spacing and uppercase styling to mimic the architectural engravings found in premium estates.

## Layout & Spacing
The layout follows a **12-column fixed grid** on desktop, centered within the viewport. Spacing is intentional and generous, utilizing an 8px base unit. 

- **Generous Gutters:** 32px gutters ensure that high-resolution property imagery "breathes" and never feels cramped.
- **Negative Space:** Content sections should be separated by at least 80px to 120px to maintain the premium, unhurried feel of an editorial magazine.
- **Refined Borders:** Use thin (1px) borders in a slightly darker sand tone to define sections without creating heavy visual noise.

## Elevation & Depth
Depth is conveyed through a "Layered Stone" metaphor rather than traditional drop shadows.
- **Glassmorphism:** Navigation bars and filter panels use a high-blur (20px) backdrop filter with a 10% opacity white/sand tint. This suggests transparency and lightness.
- **Subtle Ambient Shadows:** Cards and interactive elements use very large blur radii (32px+) with extremely low opacity (3-5%) tinted with the primary gold or charcoal. This creates a "glow" rather than a shadow.
- **Surface Tiers:**
  - Base: Warm Sand (#E8DED1)
  - Raised: White (#FFFFFF) or Glass
  - Overlays: Glassmorphic layers with 1px light borders.

## Shapes
The shape language is "Soft Geometric." We avoid the playfulness of fully rounded corners (pills) and the harshness of 0px sharp corners. 
- **0.25rem (4px)** is the standard for small elements like checkboxes or input fields.
- **0.5rem (8px)** is the standard for property cards and main containers, providing a modern but grounded feel.
- **Image Treatment:** Property images should always be rectangular or have the standard 8px radius; circular imagery is reserved only for agent avatars.

## Components
- **Buttons:** Primary buttons use the Champagne Gold background with charcoal text. They feature a subtle 1px inner "shine" border. Hover states involve a slight lift (ambient shadow increase) rather than a color shift.
- **Cards:** Property cards are the centerpiece. They use a "borderless" look with a subtle background shift on hover. Essential data (price, location) is set in Inter, while the property name is Playfair Display.
- **Input Fields:** Minimalist design with only a bottom border (2px) in Sand, which transitions to Gold on focus. Labels are always floating and set in `label-sm`.
- **Chips/Badges:** Used for property features (e.g., "Waterfront," "Penthouse"). These are semi-transparent with a 1px border, utilizing the Glassmorphism effect.
- **Search Bar:** A prominent, floating glass element with a high blur radius, typically positioned over hero imagery.
- **Lists:** Real estate specification lists (sq ft, beds, baths) use custom high-detail line icons in a thin (1px) stroke weight.