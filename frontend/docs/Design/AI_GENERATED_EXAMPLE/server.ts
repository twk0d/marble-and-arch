import express from "express";
import path from "path";
import { createServer as createViteServer } from "vite";
import { GoogleGenAI, Type } from "@google/genai";
import dotenv from "dotenv";

dotenv.config();

const app = express();
app.use(express.json());

const PORT = 3000;

// Lazy-initialized Gemini API client
let aiClient: GoogleGenAI | null = null;

function getAiClient(): GoogleGenAI {
  if (!aiClient) {
    const key = process.env.GEMINI_API_KEY;
    if (!key || key === "MY_GEMINI_API_KEY") {
      throw new Error("GEMINI_API_KEY environment variable is not defined or is placeholder. Please configure it in Settings > Secrets to enable real-time AI generation.");
    }
    aiClient = new GoogleGenAI({
      apiKey: key,
      httpOptions: {
        headers: {
          'User-Agent': 'aistudio-build',
        }
      }
    });
  }
  return aiClient;
}

// In-memory persistent database for viewing schedules
interface BookedTour {
  id: string;
  propertyId: string;
  propertyName: string;
  clientName: string;
  clientEmail: string;
  date: string;
  time: string;
  notes?: string;
  createdAt: string;
}

const bookingsDb: BookedTour[] = [
  {
    id: "tour-seed-1",
    propertyId: "estate-1",
    propertyName: "The Clifftop Pavilion",
    clientName: "Archibald Drake",
    clientEmail: "archibald@drake-estates.com",
    date: "2026-06-25",
    time: "14:00",
    notes: "Requires private helicopter coordinates and heli-pad landing confirmation.",
    createdAt: new Date().toISOString()
  }
];

// In-memory list for custom AI-curated properties to persist during development server session
let customPropertiesDb: any[] = [];

// API Endpoints
app.get("/api/health", (req, res) => {
  res.json({ status: "ok", mode: process.env.NODE_ENV });
});

// GET all bookings
app.get("/api/bookings", (req, res) => {
  res.json({ bookings: bookingsDb });
});

// POST a new booking
app.post("/api/bookings", (req, res) => {
  const { propertyId, propertyName, clientName, clientEmail, date, time, notes } = req.body;
  if (!propertyId || !propertyName || !clientName || !clientEmail || !date || !time) {
    return res.status(400).json({ error: "Missing required booking details." });
  }

  const newBooking: BookedTour = {
    id: `tour-${Date.now()}`,
    propertyId,
    propertyName,
    clientName,
    clientEmail,
    date,
    time,
    notes,
    createdAt: new Date().toISOString()
  };

  bookingsDb.push(newBooking);
  res.status(201).json({ success: true, booking: newBooking });
});

// POST to curate a new custom luxury estate via Gemini
app.post("/api/gemini/curate", async (req, res) => {
  try {
    const { prompt, vibe } = req.body;
    if (!prompt) {
      return res.status(400).json({ error: "A custom description or prompt is required to design an estate." });
    }

    console.log(`Curating a luxury property for vibe [${vibe || "Custom"}] based on: "${prompt}"`);

    // Let's decide which Unsplash image matches this request to ensure gorgeous resolution
    let selectedImage = "https://images.unsplash.com/photo-1600585154526-990dced4db0d?auto=format&fit=crop&w=1200&q=80"; // default modern glass
    const textToMatch = `${prompt} ${vibe}`.toLowerCase();

    if (textToMatch.includes("desert") || textToMatch.includes("boulder") || textToMatch.includes("oasis") || textToMatch.includes("sand")) {
      selectedImage = "https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=1200&q=80"; // desert boulder
    } else if (textToMatch.includes("coast") || textToMatch.includes("cliff") || textToMatch.includes("ocean") || textToMatch.includes("sea") || textToMatch.includes("amalfi")) {
      selectedImage = "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=1200&q=80"; // mediterranean clifftop
    } else if (textToMatch.includes("wood") || textToMatch.includes("forest") || textToMatch.includes("canopy") || textToMatch.includes("timber") || textToMatch.includes("mountain") || textToMatch.includes("cabin")) {
      selectedImage = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=1200&q=80"; // forest cabin
    } else if (textToMatch.includes("historic") || textToMatch.includes("palace") || textToMatch.includes("palazzo") || textToMatch.includes("tuscan") || textToMatch.includes("heritage")) {
      selectedImage = "https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=1200&q=80"; // stone courtyard palazzo
    } else if (textToMatch.includes("lake") || textToMatch.includes("pond") || textToMatch.includes("pavilion") || textToMatch.includes("waterfront")) {
      selectedImage = "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&w=1200&q=80"; // lakeside glass pavilion
    } else if (textToMatch.includes("skyscraper") || textToMatch.includes("sky") || textToMatch.includes("penthouse") || textToMatch.includes("loft") || textToMatch.includes("urban")) {
      selectedImage = "https://images.unsplash.com/photo-1512915922686-57c11dde9b6b?auto=format&fit=crop&w=1200&q=80"; // penthouse loft
    } else if (textToMatch.includes("brutalist") || textToMatch.includes("concrete") || textToMatch.includes("monolith")) {
      selectedImage = "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1200&q=80"; // sleek brutalist concrete
    }

    let ai;
    try {
      ai = getAiClient();
    } catch (e: any) {
      console.log("Gemini API key missing, generating simulated premium curation response...");
      // Handle fallback mode with simulated stunning content
      const simProperty = generateSimulatedProperty(prompt, vibe, selectedImage);
      customPropertiesDb.push(simProperty);
      return res.json({ property: simProperty, simulated: true });
    }

    const systemPrompt = `You are the lead architectural curator and primary creative designer for "Marble & Arch Layouts", a ultra-high-end bespoke real estate studio representing "Quiet Luxury" estates. 
Your output must be a highly structured architectural profile matching the design system specifications.
Use beautiful, descriptive vocabulary that highlights light, materials (like Lassila stone, travertine, Calacatta marble, raw cedar), and the relationship with surrounding terrain.

Choose a spectacular name that represents high architectural prestige (e.g., "The Obsidian Arch", "Le Crêt de Marbre", "Aura of Silt").
Estimate an appropriate super-luxury market price ranging from $10,000,000 to $48,000,000, depending on the scale described.
Return precisely structured data representing the custom layout including an interactive floor plan grid arrangement (on a scale of 0 to 100 for x and y).

Include 4-5 rooms in the floorPlanRooms array. Make sure their x, y center coordinates and width/height dimensions map beautifully within a 2D canvas of size 100x100, without completely overlapping each other:
Example coordinate maps:
Room 1: x: 50, y: 35, width: 30, height: 20 (Main living atrium)
Room 2: x: 18, y: 25, width: 20, height: 16 (Owner study/wing)
Room 3: x: 82, y: 25, width: 18, height: 16 (Seaside guest salon)
Room 4: x: 50, y: 75, width: 20, height: 18 (Private wellness bath)
Ensure all properties in required lists are fully populated.`;

    const instructions = `Design a bespoke luxury property tailored to this user prompt: "${prompt}". Vibe: "${vibe || 'Organic Minimalist'}".`;

    const response = await ai.models.generateContent({
      model: "gemini-3.5-flash",
      contents: instructions,
      config: {
        systemInstruction: systemPrompt,
        responseMimeType: "application/json",
        responseSchema: {
          type: Type.OBJECT,
          properties: {
            title: { type: Type.STRING, description: "Bespoke, prestigious name for the estate." },
            location: { type: Type.STRING, description: "Fictional elite location matching the design vibe (e.g., 'Aethel Cliff, Amalfi Coast', 'Obsidian Rim, Mojave Desert')." },
            price: { type: Type.STRING, description: "Format: '$XX,XXX,XXX' matching elite budget criteria." },
            rawPrice: { type: Type.INTEGER, description: "Price integer for sorting (e.g., 22000000)." },
            description: { type: Type.STRING, description: "A detailed 3-4 sentence poetical analysis of the building, natural light, and structural integration." },
            editorialQuote: { type: Type.STRING, description: "A one-sentence editorial quote describing the architectural soul." },
            categories: { type: Type.ARRAY, items: { type: Type.STRING }, description: "3-4 category keywords such as 'Waterfront', 'Modern Minimalist', 'Historic', 'Wellness'." },
            specs: {
              type: Type.OBJECT,
              properties: {
                beds: { type: Type.INTEGER },
                baths: { type: Type.INTEGER },
                sqft: { type: Type.INTEGER },
                yearBuilt: { type: Type.INTEGER },
                materials: { type: Type.ARRAY, items: { type: Type.STRING } }
              },
              required: ["beds", "baths", "sqft", "yearBuilt", "materials"]
            },
            features: { type: Type.ARRAY, items: { type: Type.STRING }, description: "4-5 exceptional ultra-luxury features." },
            floorPlanKeyHighlights: { type: Type.ARRAY, items: { type: Type.STRING } },
            floorPlanRooms: {
              type: Type.ARRAY,
              items: {
                type: Type.OBJECT,
                properties: {
                  name: { type: Type.STRING, description: "Name of the room (e.g., 'Atrium Living Cell', 'Thermae & Mist Basin')." },
                  description: { type: Type.STRING, description: "Philosophical design description of this specific room." },
                  dimensions: { type: Type.STRING, description: "Dimensions in feet, e.g. '28' x 20''" },
                  x: { type: Type.INTEGER, description: "Relative center X coordinate (0-100) on a 100x100 canvas grid." },
                  y: { type: Type.INTEGER, description: "Relative center Y coordinate (0-100) on a 100x100 canvas grid." },
                  width: { type: Type.INTEGER, description: "Relative footprint width on canvas (e.g. 15 to 30)." },
                  height: { type: Type.INTEGER, description: "Relative footprint height on canvas (e.g. 12 to 20)." }
                },
                required: ["name", "description", "dimensions", "x", "y", "width", "height"]
              }
            }
          },
          required: [
            "title",
            "location",
            "price",
            "rawPrice",
            "description",
            "editorialQuote",
            "categories",
            "specs",
            "features",
            "floorPlanKeyHighlights",
            "floorPlanRooms"
          ]
        },
      },
    });

    const outputText = response.text;
    if (!outputText) {
      throw new Error("Empty response returned from Gemini curation agent.");
    }

    const curatedData = JSON.parse(outputText.trim());
    
    // Inject the selected high-quality image and standard agent info
    curatedData.id = `custom-${Date.now()}`;
    curatedData.imageUrl = selectedImage;
    curatedData.isAiGenerated = true;
    curatedData.agent = {
      name: "Marcus Sterling",
      role: "Senior Architectural Director, Modernist Portfolio",
      avatarUrl: "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=400&h=400&q=80",
      phone: "+1 (800) 555-0199"
    };

    customPropertiesDb.push(curatedData);
    res.json({ property: curatedData, simulated: false });

  } catch (error: any) {
    console.error("Error in /api/gemini/curate:", error);
    res.status(500).json({ error: error.message || "An error occurred during Gemini AI curation." });
  }
});

// GET all curated custom properties
app.get("/api/custom-properties", (req, res) => {
  res.json({ properties: customPropertiesDb });
});

// Helper for offline / simulated mode
function generateSimulatedProperty(prompt: string, vibe: string, imageUrl: string) {
  const isDesert = prompt.toLowerCase().includes("desert") || prompt.toLowerCase().includes("boulder");
  const title = isDesert ? "The Silt & Stone Pavilion" : "The Glass Horizon Villa";
  const location = isDesert ? "Amargosa Basin, Mojave Heights" : "Nordic Fjords Overlook, Norway";
  const price = isDesert ? "$16,400,000" : "$21,800,000";

  return {
    id: `simulated-${Date.now()}`,
    title,
    location,
    price,
    rawPrice: isDesert ? 16400000 : 21800000,
    imageUrl,
    description: `A stunning response designed specifically for your requested theme: "${prompt}". Combining pure monolithic stone partitions with suspended low-iron structural glass, it embraces local light contours while providing high thermal privacy. Crafted with standard 8px structural curvatures and expansive negative spaces.`,
    editorialQuote: "An unhurried conversation between light-reflective travertine cliffs and transparent glass membranes.",
    categories: ["AI-Curated", vibe || "Organic Minimalist"],
    specs: {
      beds: 4,
      baths: 5,
      sqft: 7200,
      yearBuilt: 2026,
      materials: ["Lassila Travertine", "Exposed Cast Concrete", "Belgian Bronze framing", "Cedarwood planks"]
    },
    features: [
      "Glazed retractable curtain walls facing panoramic vistas",
      "Central open skylight atrium with tranquil water fountain",
      "Subterranean geothermal stone wellness cooling vault",
      "Private helipad terrace with integrated stone lighting"
    ],
    floorPlanKeyHighlights: [
      "Horizontal linear circulation optimizing site horizons",
      "Wrapped entirely around a central water garden courtyard",
      "Separate elevated guest pavilion linked by a glass bridge"
    ],
    floorPlanRooms: [
      { name: "Atrium Grand Lounge", description: "Expansive high-ceiling lounge opening completely onto the scenic pools.", dimensions: "32' x 22'", x: 50, y: 35, width: 30, height: 22 },
      { name: "The Alchemist Suite", description: "Bespoke sleeping quarters flanked by textured stone slabs and linear glass voids.", dimensions: "18' x 16'", x: 18, y: 25, width: 18, height: 16 },
      { name: "Scented Stone Thermae", description: "Full black cedar bath suite containing a deep cold plunge water basin.", dimensions: "16' x 14'", x: 82, y: 35, width: 16, height: 16 },
      { name: "Tasting Hearth", description: "Curated kitchen and custom travertine island, backed by charcoal and oak cabinetry.", dimensions: "18' x 16'", x: 50, y: 75, width: 18, height: 14 }
    ],
    agent: {
      name: "Marcus Sterling",
      role: "Senior Architectural Director, Modernist Portfolio",
      avatarUrl: "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=400&h=400&q=80",
      phone: "+1 (800) 555-0199"
    },
    isAiGenerated: true
  };
}

// Vite and static asset serving
async function startServer() {
  if (process.env.NODE_ENV !== "production") {
    console.log("Setting up Vite middleware for developmental rendering...");
    const vite = await createViteServer({
      server: { middlewareMode: true },
      appType: "spa",
    });
    app.use(vite.middlewares);
  } else {
    console.log("Setting up production static file serving...");
    const distPath = path.join(process.cwd(), "dist");
    app.use(express.static(distPath));
    app.get("*", (req, res) => {
      res.sendFile(path.join(distPath, "index.html"));
    });
  }

  app.listen(PORT, "0.0.0.0", () => {
    console.log(`Marble & Arch Dev Server successfully running on http://localhost:${PORT}`);
  });
}

startServer();
