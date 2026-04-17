import { Property } from './types';

export const ESTABLISH_AGENTS = {
  elena: {
    name: "Elena Vane",
    role: "Principal Client Partner, Mediterranean Portfolio",
    avatarUrl: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=400&h=400&q=80",
    phone: "+1 (800) 555-0142"
  },
  marcus: {
    name: "Marcus Sterling",
    role: "Senior Architectural Director, Modernist Portfolio",
    avatarUrl: "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=400&h=400&q=80",
    phone: "+1 (800) 555-0199"
  },
  seraphina: {
    name: "Seraphina Stone",
    role: "Director of Curation, Historic Estates",
    avatarUrl: "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&w=400&h=400&q=80",
    phone: "+1 (800) 555-0178"
  }
};

export const INITIAL_PROPERTIES: Property[] = [
  {
    id: "estate-1",
    title: "The Clifftop Pavilion",
    location: "Aethel Cliff, Amalfi Coast",
    price: "$24,500,000",
    rawPrice: 24500000,
    imageUrl: "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=1200&q=80",
    description: "Cradled by ancient limestone cliffs high above the cobalt waters of the Amalfi Coast, The Clifftop Pavilion is a masterpiece of architectural gravity. Seamless structural concrete meets warm limestone flooring, giving the entire pavilion a sense of ancient permanence combined with modern airiness. The building steps down the hillside, offering panoramic sea views from every terrace, centered around a 20-meter heated limestone infinity pool.",
    editorialQuote: "An architectural dialogue between volcanic cliffs and minimal glass frames, evoking a state of absolute serenity.",
    categories: ["Waterfront", "Mediterranean", "Villa"],
    specs: {
      beds: 5,
      baths: 6,
      sqft: 8500,
      yearBuilt: 2024,
      materials: ["Lassila stone", "Exposed concrete", "Travertine marble", "Belgian bronze"]
    },
    features: [
      "Glass gallery with retractable motorized walls",
      "Private funicular to a quiet beach cave",
      "Limestone infinity pool projecting into the sea",
      "Restored subterranean Roman olive-press wine vault",
      "Outdoor stone pavilion kitchen and woodfired hearth"
    ],
    floorPlanKeyHighlights: [
      "Centripetal gallery connecting all wings",
      "Master sanctuary occupies its own floating floor level",
      "Direct double-door courtyard entry from all suites"
    ],
    floorPlanRooms: [
      { name: "Grand Vista Gallery", description: "Enormous open-concept reception hall that integrates living and dining under a raw-wood coffered ceiling. Faces the main shoreline terrace.", dimensions: "42' x 24'", x: 50, y: 35, width: 35, height: 25 },
      { name: "Ocean Master Suite", description: "A private cantilevered suite featuring an outdoor slate shower, custom built-in oak wardrobes, and a glass wellness alcove.", dimensions: "22' x 18'", x: 18, y: 25, width: 22, height: 16 },
      { name: "The Vault Wine Studio", description: "Built directly into the volcanic cavern behind the property, climate controlled and styled with stone-slab tasting tables.", dimensions: "14' x 16'", x: 82, y: 25, width: 14, height: 16 },
      { name: "The Culinaric Atrium", description: "Bespoke travertine chef's workspace complete with integrated Gaggenau appliances, stone basins, and hidden pantry access.", dimensions: "18' x 16'", x: 50, y: 70, width: 18, height: 16 },
      { name: "East Sanctuary Suite", description: "Stunning private bedroom with an en-suite bath carved in monolithic green quartzite, looking out to the stone courtyard.", dimensions: "16' x 15'", x: 18, y: 70, width: 16, height: 16 }
    ],
    agent: ESTABLISH_AGENTS.elena
  },
  {
    id: "estate-2",
    title: "The Obsidian Ridge",
    location: "High Desert Overlook, Joshua Tree",
    price: "18,200,000",
    rawPrice: 18200000,
    imageUrl: "https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=1200&q=80",
    description: "Nestled quietly among massive pre-historic boulders and sweeping desert sands, The Obsidian Ridge is a private wellness fortress. Utilizing deep textured dark basalt blocks and towering bronze-anodized glazing, the villa is nearly invisible under the shifting desert sky. It features an innovative central courtyard that protects a calm zen stone garden, drawing cool air into the living quarters without disrupting the desert landscape.",
    editorialQuote: "A minimalist basalt fortress acting as an astronomical lens into the silent, star-filled Mojave nights.",
    categories: ["Desert Oasis", "Penthouse", "Modern Minimalist"],
    specs: {
      beds: 3,
      baths: 4,
      sqft: 6100,
      yearBuilt: 2025,
      materials: ["Textured black basalt", "Oxidized brass", "Polished obsidian terrazzo", "Cedar wood"]
    },
    features: [
      "Rooftop celestial viewing deck with automated telescope",
      "Subterranean pool cooled by geothermal stone loops",
      "Protected inner stone-garden atrium with trickling water monolith",
      "Off-grid solar and mountain spring water filtration",
      "Private hammam carved entirely from black slate"
    ],
    floorPlanKeyHighlights: [
      "Wrapped around an open celestial void garden",
      "All master suites feature outdoor rock pool tubs",
      "Integrated smart acoustics tuned for high quietness"
    ],
    floorPlanRooms: [
      { name: "The Sun & Shade Hearth", description: "Main lounge with massive glazed walling that slides fully into basalt pocket walls to merge with the desert rim.", dimensions: "35' x 20'", x: 50, y: 35, width: 30, height: 20 },
      { name: "Western Rock Suite", description: "Suite framed with polished obsidian floors. Outfitted with external cedar deck and an engineered hot rock tub nestled in actual boulders.", dimensions: "18' x 18'", x: 18, y: 30, width: 18, height: 18 },
      { name: "Lunar Observation Loft", description: "Top elevation platform featuring a minimal reading deck, dark star-watching library, and custom structural dome.", dimensions: "14' x 12'", x: 50, y: 75, width: 14, height: 12 },
      { name: "The Hammam & Bath", description: "Black basalt stone sanctuary with integrated steam paneling, heated stone lounges, and a cold plunge fountain.", dimensions: "16' x 14'", x: 82, y: 30, width: 16, height: 14 }
    ],
    agent: ESTABLISH_AGENTS.marcus
  },
  {
    id: "estate-3",
    title: "Palazzo di Pietra",
    location: "Chianti Foothills, Tuscany",
    price: "$19,800,000",
    rawPrice: 19800000,
    imageUrl: "https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=1200&q=80",
    description: "Palazzo di Pietra is an exquisite conversation between a 14th-century stonemason's courtyard and the heights of contemporary metropolitan architecture. The ancient heavy-timber ceilings and original stone walls have been structurally braced using delicate, feather-thin matte black steel beams. Massive light wells puncture the courtyard, illuminating original terracotta masonry and modern custom furniture curated by European master designers.",
    editorialQuote: "Where medieval masonry is lightened by modern steel plates, letting historic narratives breath in open sunlight.",
    categories: ["Historic", "Mediterranean", "Palazzo"],
    specs: {
      beds: 6,
      baths: 7,
      sqft: 11200,
      yearBuilt: 1392,
      materials: ["Medieval stone", "Matte black structural steel", "Calacatta gold marble", "Aged oak planks"]
    },
    features: [
      "Courtyard garden framed by original 14th-century stone arches",
      "Extensive vaulted cellars with space for 3,000 wine reserves",
      "Modernist library mezzanine wrapped in continuous custom steel bookcases",
      "Private olive grove producing single-estate cold-pressed oil",
      "Heated stone pavilion that converts into an indoor conservatory"
    ],
    floorPlanKeyHighlights: [
      "Symmetrical Tuscan layouts around a quadrangle courtyard",
      "Double-height gallery space with original frescoes",
      "Separate guest cottage accessible via stone bridge"
    ],
    floorPlanRooms: [
      { name: "Corte d'Onore (Central Courtyard)", description: "Open-air limestone courtyard with a modern bronze fountain, olive trees, and original arched corridors.", dimensions: "30' x 30'", x: 50, y: 50, width: 30, height: 30 },
      { name: "The Vaulted Library", description: "Double-height chamber containing an original fireplace, custom steel floor mezzanines, and thousands of volumes.", dimensions: "24' x 16'", x: 18, y: 50, width: 20, height: 16 },
      { name: "Salone Affrescato", description: "Grand reception hall sporting beautifully preserved ceiling frescoes, white marble hearth, and glass skywells.", dimensions: "32' x 18'", x: 50, y: 15, width: 32, height: 16 },
      { name: "The Gastronomy Vault", description: "An expansive kitchen layout blending ancient flagstone flooring with high-end steel islands and an integrated brick hearth.", dimensions: "18' x 16'", x: 82, y: 50, width: 18, height: 16 }
    ],
    agent: ESTABLISH_AGENTS.seraphina
  },
  {
    id: "estate-4",
    title: "The Sanctuary Canopy",
    location: "Cascadia Fir Reserve, Oregon",
    price: "$12,400,000",
    rawPrice: 12400000,
    imageUrl: "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=1200&q=80",
    description: "Suspended masterfully within the ancient fir reserves of the Pacific Northwest, The Sanctuary Canopy explores lightweight architectural impact. Anchored on minimal rock pier footings, the glass-and-steel volumes look as if float among the dense emerald canopy. A continuous ribbon of clear cedar wrapping guides the interior corridors, seamlessly fading the borders between human comfort and native forest wilderness.",
    editorialQuote: "An elegant glass prism nestled at the height of fir branches, where the green canopy becomes your primary interior tapestry.",
    categories: ["Modern Minimalist", "Villa", "Wellness Hideaway"],
    specs: {
      beds: 4,
      baths: 4,
      sqft: 5400,
      yearBuilt: 2023,
      materials: ["Pacific red cedar", "Raw structural steel", "Mist silver slate", "Triple-pane low-iron glass"]
    },
    features: [
      "Continuous suspended wrap-around cedar viewing platform",
      "Master bath features a fully glass floor panel over a trickling forest stream",
      "Wellness tea pavilion under custom skylights",
      "Direct hiking path access into thousands of acres of private preserve",
      "Passive ventilation system harvesting mountain breezes"
    ],
    floorPlanKeyHighlights: [
      "Radial structural alignment with surrounding fir clusters",
      "Modular living quarters connected via open glass bridges",
      "No visible supports from the main facade, highlighting suspension"
    ],
    floorPlanRooms: [
      { name: "The Emerald Aviary Lounge", description: "Fully glass room suspended over the river canyon, providing views of passing high-altitude hawks and active forest life.", dimensions: "30' x 18'", x: 50, y: 35, width: 28, height: 18 },
      { name: "The Cedar Sanctuary Suite", description: "Master suite wrapped in warm cedar planks, showcasing a raised stone slab fire bed and direct hot spring pool accessibility.", dimensions: "18' x 16'", x: 18, y: 45, width: 18, height: 16 },
      { name: "Forest Scribe Library", description: "Cozy private library and study room featuring an integrated oak desk, steel bookshelves, and visual canyon views.", dimensions: "15' x 12'", x: 82, y: 45, width: 15, height: 12 }
    ],
    agent: ESTABLISH_AGENTS.marcus
  }
];
