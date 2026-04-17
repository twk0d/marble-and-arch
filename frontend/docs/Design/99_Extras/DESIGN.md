---
name: Nocturnal Elegance
colors:
  surface: '#131313'
  surface-dim: '#131313'
  surface-bright: '#393939'
  surface-container-lowest: '#0e0e0e'
  surface-container-low: '#1c1b1b'
  surface-container: '#201f1f'
  surface-container-high: '#2a2a2a'
  surface-container-highest: '#353534'
  on-surface: '#e5e2e1'
  on-surface-variant: '#d1c5b4'
  inverse-surface: '#e5e2e1'
  inverse-on-surface: '#313030'
  outline: '#9a8f80'
  outline-variant: '#4e4639'
  surface-tint: '#e9c176'
  primary: '#e9c176'
  on-primary: '#412d00'
  primary-container: '#c5a059'
  on-primary-container: '#4e3700'
  inverse-primary: '#775a19'
  secondary: '#d4c5a7'
  on-secondary: '#382f1b'
  secondary-container: '#50462f'
  on-secondary-container: '#c2b397'
  tertiary: '#c8c6c5'
  on-tertiary: '#313030'
  tertiary-container: '#a7a5a5'
  on-tertiary-container: '#3b3b3b'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#ffdea5'
  primary-fixed-dim: '#e9c176'
  on-primary-fixed: '#261900'
  on-primary-fixed-variant: '#5d4201'
  secondary-fixed: '#f1e1c2'
  secondary-fixed-dim: '#d4c5a7'
  on-secondary-fixed: '#221b08'
  on-secondary-fixed-variant: '#50462f'
  tertiary-fixed: '#e5e2e1'
  tertiary-fixed-dim: '#c8c6c5'
  on-tertiary-fixed: '#1b1b1b'
  on-tertiary-fixed-variant: '#474746'
  background: '#131313'
  on-background: '#e5e2e1'
  surface-variant: '#353534'
typography:
  display-lg:
    fontFamily: Playfair Display
    fontSize: 64px
    fontWeight: '700'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  display-lg-mobile:
    fontFamily: Playfair Display
    fontSize: 40px
    fontWeight: '700'
    lineHeight: '1.2'
  headline-md:
    fontFamily: Playfair Display
    fontSize: 32px
    fontWeight: '600'
    lineHeight: '1.3'
  body-lg:
    fontFamily: DM Sans
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: 0.01em
  body-md:
    fontFamily: DM Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
  label-sm:
    fontFamily: DM Sans
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1.0'
    letterSpacing: 0.1em
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  unit: 8px
  container-max: 1200px
  gutter: 24px
  margin-mobile: 20px
  margin-desktop: 64px
---

## Brand & Style
The design system embodies a world of "Nocturnal Elegance," pivoting the established luxury brand into a high-contrast, immersive dark environment. The target audience remains high-end clientele, but the emotional response shifts from airy openness to intimate, curated sophistication.

The design style is a hybrid of **Minimalism** and **Glassmorphism**, utilizing deep charcoal surfaces and warm golden accents. It prioritizes heavy whitespace (used here as "negative dark space"), exquisite serif typography, and subtle luminosity. The aesthetic evokes the atmosphere of a private lounge or a boutique gallery at night—refined, quiet, and exclusive.

## Colors
The palette is rooted in a deep, layered dark theme. The foundation is a "True Charcoal" (#121212), which provides a stable, low-glare background. To maintain the brand’s warmth, interactive surfaces and containers utilize a "Warm Obsidian" (#1E1B16), introducing a faint brownish-gold undertone to the dark tones.

The primary accent is "Antique Gold" (#C5A059), used for high-importance actions and branding elements. Text and iconography utilize "Champagne Silk" (#E5D5B7) rather than pure white to reduce eye strain and maintain the luxury feel. Success and error states should be muted (e.g., sage greens or deep garnets) to avoid breaking the sophisticated atmosphere.

## Typography
The typographic hierarchy relies on the contrast between the traditional authority of **Playfair Display** and the modern clarity of **DM Sans**. 

Headlines should be set in Playfair Display with generous line heights to allow the serif details to breathe against the dark background. For body text, DM Sans is used to ensure maximum legibility at smaller scales. All uppercase labels should include increased letter spacing to enhance the "luxury editorial" feel. Avoid pure white text; use the secondary "Champagne Silk" color for primary content and a 60% opacity version for secondary information.

## Layout & Spacing
This design system employs a **Fixed Grid** model for desktop and a fluid model for mobile. On desktop, the central container is capped at 1200px to maintain focus and prevent text lines from becoming unreadably long.

The spacing rhythm is based on an 8px base unit, but luxury is expressed through "over-spacing." Increase vertical margins between sections (e.g., 120px or 160px) to create a sense of calm and importance. Elements should feel like they are floating in an expansive, dark void rather than being cramped together.

## Elevation & Depth
Elevation is expressed through **Tonal Layering** and **Glassmorphism** rather than traditional heavy shadows.

1.  **Level 0 (Base):** #121212.
2.  **Level 1 (Cards/Sections):** #1C1C1C with a 1px "Antique Gold" border at 10% opacity.
3.  **Level 2 (Modals/Overlays):** A semi-transparent blur (Backdrop Filter: 20px) using #1E1B16 at 80% opacity.

Instead of black shadows, use "Glow Shadows" for elevated golden elements—a very soft, low-opacity spread of the primary gold color to simulate light reflecting off a metallic surface.

## Shapes
The shape language is "Architectural Softness." We use a **Soft (1)** roundedness level (0.25rem/4px) to provide a modern, precision-engineered feel that avoids the "bubbly" look of higher rounding. 

Cards and containers use 4px radii, while large hero images or decorative elements may use 8px (rounded-lg) to soften the edges of the frame. This slight rounding suggests human craft and comfort without sacrificing the professional, high-end structure.

## Components
-   **Buttons:** Primary buttons are solid "Antique Gold" with dark text (#121212). Secondary buttons use a 1px gold border with "Champagne Silk" text. All buttons have a subtle 2px letter spacing.
-   **Inputs:** Fields are dark (#1C1C1C) with a bottom-border only (1px gold) to mimic architectural sketches. Focus states illuminate the gold border and add a faint golden outer glow.
-   **Cards:** Use the "Level 1" elevation. On hover, the 10% gold border increases to 40% opacity to provide tactile feedback.
-   **Lists:** Items are separated by hair-line dividers (#C5A059 at 10% opacity). 
-   **Chips/Tags:** Small, outlined elements with "label-sm" typography.
-   **Navigation:** A sticky top-bar using the glassmorphic blur effect to maintain context of the background content while scrolling.