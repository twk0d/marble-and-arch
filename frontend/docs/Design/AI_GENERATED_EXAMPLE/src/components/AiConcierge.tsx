import React, { useState, useRef, useEffect } from 'react';
import { Sparkles, Send, Compass, ArrowRight, Loader2, CheckCircle, HelpCircle } from 'lucide-react';
import { Property } from '../types';

interface AiConciergeProps {
  onPropertyCurated: (newProperty: Property) => void;
  isDarkCanvas: boolean;
}

interface ChatMessage {
  id: string;
  sender: 'user' | 'concierge';
  text: string;
  timestamp: string;
  curatedProperty?: Property;
  loadingStatus?: string;
}

export default function AiConcierge({ onPropertyCurated, isDarkCanvas }: AiConciergeProps) {
  const [messages, setMessages] = useState<ChatMessage[]>([
    {
      id: 'welcome',
      sender: 'concierge',
      text: "Welcome to the Marble & Arch AI Studio. I am your lead architectural draftsman. Describe the physical parameters, emotional textures, and landscape of your ideal estate, and I will assemble a bespoke, interactive 2D layout and architectural blueprint for you on the fly.",
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    }
  ]);

  const [inputPrompt, setInputPrompt] = useState('');
  const [selectedVibe, setSelectedVibe] = useState('Organic Minimalist');
  const [isSynthesizing, setIsSynthesizing] = useState(false);
  const [loadingStep, setLoadingStep] = useState(0);
  const [activePropertyCreated, setActivePropertyCreated] = useState<Property | null>(null);

  const endOfChatRef = useRef<HTMLDivElement>(null);

  const designPresets = [
    { prompt: "A low-slung concrete villa on a Greek volcanic ridge, complete with a natural basalt mineral bath.", label: "Greek Ridge" },
    { prompt: "A glass pavilion suspended in rain forest trees with towering red cedar beams and high moss gardens.", label: "Forest Sanctuary" },
    { prompt: "A sleek black obsidian monolith in Joshua Tree desert with a celestial stardeck and cooled spa caves.", label: "Desert Obsidian" },
    { prompt: "A historic stone barn in the Swiss Alps, fortified with razor-thin structural steel arches and a glazed skylit lounge.", label: "Alpine Palazzo" }
  ];

  const synthesisSteps = [
    "Reading landscape textures & site orientation...",
    "Drafting volumetric concrete columns on grid...",
    "Framing triple-pane low-iron architectural glazing...",
    "Mapping fluid centripetal floor plans...",
    "Securing exclusive client partner licenses..."
  ];

  useEffect(() => {
    endOfChatRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, isSynthesizing, loadingStep]);

  // Loading animation sequence
  useEffect(() => {
    let interval: NodeJS.Timeout;
    if (isSynthesizing) {
      interval = setInterval(() => {
        setLoadingStep((prev) => {
          if (prev < synthesisSteps.length - 1) {
            return prev + 1;
          }
          return prev;
        });
      }, 2500);
    } else {
      setLoadingStep(0);
    }
    return () => clearInterval(interval);
  }, [isSynthesizing]);

  const handleSubmitPrompt = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputPrompt.trim()) return;

    const userText = inputPrompt;
    setInputPrompt('');
    setIsSynthesizing(true);
    setActivePropertyCreated(null);

    // Append user message
    const userMsg: ChatMessage = {
      id: `user-${Date.now()}`,
      sender: 'user',
      text: userText,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };

    setMessages((prev) => [...prev, userMsg]);

    try {
      const res = await fetch('/api/gemini/curate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          prompt: userText,
          vibe: selectedVibe
        })
      });

      if (res.ok) {
        const data = await res.json();
        
        const conciergeMsg: ChatMessage = {
          id: `concierge-${Date.now()}`,
          sender: 'concierge',
          text: `Architectural compilation finalized. I have crafted "${data.property.title}" specifically suited for the parameters of your request.`,
          timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
          curatedProperty: data.property
        };
        
        setMessages((prev) => [...prev, conciergeMsg]);
        setActivePropertyCreated(data.property);
      } else {
        const errorData = await res.json();
        throw new Error(errorData.error || "Synthesis failed.");
      }
    } catch (err: any) {
      console.warn("Real-time curate error, executing simulated fallback client-side:", err);
      // Fallback local simulation in case of connection failure or key missing
      const simulatedData = getLocalBackupCuration(userText, selectedVibe);
      
      const conciergeMsg: ChatMessage = {
        id: `concierge-${Date.now()}`,
        sender: 'concierge',
        text: `Bespoke layout complete (Simulated). I have drafted "${simulatedData.title}" corresponding to the Mediterranean / Minimalist guidelines.`,
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
        curatedProperty: simulatedData
      };
      
      setMessages((prev) => [...prev, conciergeMsg]);
      setActivePropertyCreated(simulatedData);
    } finally {
      setIsSynthesizing(false);
    }
  };

  const handleInjectProperty = (property: Property) => {
    onPropertyCurated(property);
    // Add success confirmation to chat
    const systemAck: ChatMessage = {
      id: `ack-${Date.now()}`,
      sender: 'concierge',
      text: `"${property.title}" has been successfully added to the active Portfolio list! You can now explore its interactive blueprint grid and schedule private tour requests directly in the Portfolio page.`,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };
    setMessages((prev) => [...prev, systemAck]);
    setActivePropertyCreated(null);
  };

  // Static fallback generator
  const getLocalBackupCuration = (promptText: string, vibeStyle: string): Property => {
    const isForest = promptText.toLowerCase().includes("forest") || promptText.toLowerCase().includes("tree") || promptText.toLowerCase().includes("wood");
    const isDesert = promptText.toLowerCase().includes("desert") || promptText.toLowerCase().includes("joshua") || promptText.toLowerCase().includes("sand") || promptText.toLowerCase().includes("boulder");
    
    let title = "The Mist & Steel Atrium";
    let location = "Cascadia Canopy Heights, Oregon";
    let price = "$15,600,000";
    let img = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=1200&q=80";

    if (isDesert) {
      title = "The Basalt Ridge Sanctuary";
      location = "Mojave Rim, Joshua Desert";
      price = "$19,200,000";
      img = "https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=1200&q=80";
    } else if (!isForest) {
      title = "Monolito di Arenaria";
      location = "Paros Cliffs, Cyclades Islands";
      price = "$26,000,000";
      img = "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=1200&q=80";
    }

    return {
      id: `sim-${Date.now()}`,
      title,
      location,
      price,
      rawPrice: 19200000,
      imageUrl: img,
      description: `Designed in alignment with client specs: "${promptText}". Sculpted basalt block formations form a windproof spine against deep terrain drafts, while thin 1px Belgian steel frames carry the massive skylight canopies. A double-height reflection atrium holds a calming zen garden that catches morning rays.`,
      editorialQuote: "A masterwork balancing the protective density of hand-dressed stone with the weightlessness of raw glass sheets.",
      categories: ["AI-Curated", vibeStyle],
      specs: {
        beds: 4,
        baths: 4,
        sqft: 6800,
        yearBuilt: 2026,
        materials: ["Volcanic basalt", "Travertine slabs", "White cedar planking", "Refined low-iron glazing"]
      },
      features: [
        "Retractable architectural glazed sliding pocket doors",
        "Subterranean geothermal water therapy spa cavity",
        "Rooftop celestial skydeck with automated focus glass",
        "Central open garden atrium displaying local monoliths"
      ],
      floorPlanKeyHighlights: [
        "Centripetal corridor wrapping around the indoor courtyard",
        "Master suite cantilevered 15 feet over local boulder terrain",
        "Separate executive office module reached via glass conservatory"
      ],
      floorPlanRooms: [
        { name: "Monolith Lounge", description: "Vast primary living hall dominated by huge raw stone fireplace and glazed sky vents.", dimensions: "32' x 20'", x: 50, y: 35, width: 28, height: 20 },
        { name: "Cascading Thermae Master", description: "Bespoke sleeping wing showing open limestone bathtubs looking onto private stone niches.", dimensions: "20' x 16'", x: 18, y: 25, width: 20, height: 16 },
        { name: "The Tasting Court", description: "Beautiful stone-island culinary zone complete with luxury oak fittings.", dimensions: "16' x 16'", x: 82, y: 35, width: 16, height: 16 },
        { name: "Scenic Scribe Studio", description: "Cozy library sanctuary with detailed skylights for panoramic work environments.", dimensions: "14' x 14'", x: 50, y: 75, width: 14, height: 14 }
      ],
      agent: {
        name: "Marcus Sterling",
        role: "Senior Architectural Director, Modernist Portfolio",
        avatarUrl: "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=400&h=400&q=80",
        phone: "+1 (800) 555-0199"
      },
      isAiGenerated: true
    };
  };

  return (
    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 min-h-[calc(100vh-170px)] items-stretch">
      {/* Search Specs / Architectural Presets sidebar */}
      <div className={`p-6 rounded-lg flex flex-col justify-between ${isDarkCanvas ? 'bg-[#1e1e1e] border border-[#4e4639]/20 text-white' : 'bg-white border border-[#e8ded1]/30'} shadow-sm`}>
        <div className="space-y-6">
          <div>
            <span className={`text-[9px] tracking-widest uppercase font-bold ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
              CAD PRESETS
            </span>
            <h3 className="font-serif text-xl font-bold mt-0.5">
              Drafting Parameters
            </h3>
            <p className={`text-xs mt-1.5 leading-relaxed ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-500'}`}>
              Select an architectural baseline preset or choose a layout vibe to direct the compilation engine.
            </p>
          </div>

          {/* Vibe selection */}
          <div className="space-y-2">
            <label className="text-[10px] tracking-wider uppercase font-semibold text-neutral-400">
              Structural Vibe
            </label>
            <div className="grid grid-cols-2 gap-2 matches-touch-targets">
              {['Organic Minimalist', 'Sleek Brutalist', 'Mediterranean Stone', 'Glass Pavilion'].map((vibe) => (
                <button
                  key={vibe}
                  onClick={() => setSelectedVibe(vibe)}
                  className={`py-2 text-[10px] font-bold uppercase tracking-wider rounded text-center transition-all duration-300 border cursor-pointer ${
                    selectedVibe === vibe
                      ? (isDarkCanvas ? 'bg-[#ffdea5] text-black border-transparent' : 'bg-[#775a19] text-white border-transparent')
                      : (isDarkCanvas ? 'bg-neutral-800 border-[#4e4639]/30 text-stone-300 hover:bg-neutral-700' : 'bg-white border-[#e8ded1] text-secondary-oncontainer hover:bg-stone-50')
                  }`}
                >
                  {vibe.split(' ')[0]}
                </button>
              ))}
            </div>
          </div>

          {/* Presets List */}
          <div className="space-y-2 pt-4 border-t border-stone-500/10">
            <label className="text-[10px] tracking-wider uppercase font-semibold text-neutral-400 block mb-2">
              Bespoke Presets
            </label>
            <div className="space-y-2">
              {designPresets.map((preset) => (
                <button
                  key={preset.label}
                  onClick={() => setInputPrompt(preset.prompt)}
                  className={`w-full text-left p-3 rounded text-xs transition-all duration-300 border flex items-start gap-2.5 cursor-pointer ${
                    inputPrompt === preset.prompt
                      ? (isDarkCanvas ? 'bg-[#ffdea5]/10 border-[#ffdea5] text-white' : 'bg-[#775a19]/5 border-[#775a19] text-[#775a19] font-medium')
                      : (isDarkCanvas ? 'bg-neutral-800/40 border-[#4e4639]/20 text-stone-300 hover:bg-neutral-800' : 'bg-stone-50 border-stone-200/50 text-neutral-700 hover:bg-stone-100')
                  }`}
                >
                  <Sparkles className="w-3.5 h-3.5 mt-0.5 text-stone-400 shrink-0" />
                  <div>
                    <strong className="block text-[11px] mb-0.5">{preset.label}</strong>
                    <span className="line-clamp-2 text-[10px] opacity-85 leading-relaxed">{preset.prompt}</span>
                  </div>
                </button>
              ))}
            </div>
          </div>
        </div>

        <div className="mt-6 pt-4 border-t border-stone-500/10 flex items-center gap-3 text-xs opacity-80 leading-relaxed font-label-sm">
          <HelpCircle strokeWidth={1} className="w-8 h-8 shrink-0 text-stone-400" />
          <p className="text-[10px]">
            Each curated villa creates detailed blueprint specifications and maps custom coordinates instantly.
          </p>
        </div>
      </div>

      {/* Main Chat & Synthesis Screen */}
      <div className={`lg:col-span-2 p-6 rounded-lg flex flex-col justify-between items-stretch min-h-[480px] ${isDarkCanvas ? 'bg-[#1e1e1e] border border-[#4e4639]/20' : 'bg-white border border-[#e8ded1]/30'} shadow-sm`}>
        {/* Chat message logs */}
        <div className="flex-grow overflow-y-auto space-y-4 mb-6 pr-2 max-h-[450px]">
          {messages.map((msg) => {
            const isConcierge = msg.sender === 'concierge';
            return (
              <div 
                key={msg.id}
                className={`flex flex-col ${isConcierge ? 'items-start' : 'items-end'}`}
              >
                <div className={`max-w-[85%] rounded p-4 text-xs leading-relaxed ${
                  isConcierge
                    ? (isDarkCanvas ? 'bg-[#2a2929] text-stone-100' : 'bg-[#fcf9f8] text-neutral-800')
                    : (isDarkCanvas ? 'bg-[#ffdea5] text-black font-medium' : 'bg-[#775a19] text-white font-medium')
                }`}>
                  <p>{msg.text}</p>
                  
                  {/* Curated result rendering card directly inside message stream */}
                  {msg.curatedProperty && (
                    <div className={`mt-4 p-4 rounded border text-left ${isDarkCanvas ? 'bg-[#1c1b1b] border-[#4e4639]/30' : 'bg-white border-[#e8ded1]/60'}`}>
                      <div className="relative aspect-[16/9] w-full rounded overflow-hidden mb-3 bg-stone-100">
                        <img 
                          src={msg.curatedProperty.imageUrl} 
                          alt={msg.curatedProperty.title}
                          referrerPolicy="no-referrer"
                          className="w-full h-full object-cover"
                        />
                      </div>
                      <span className={`text-[9px] tracking-widest font-mono uppercase ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
                        {msg.curatedProperty.location}
                      </span>
                      <h4 className={`font-serif text-base font-bold mt-0.5 ${isDarkCanvas ? 'text-white' : 'text-neutral-900'}`}>
                        {msg.curatedProperty.title}
                      </h4>
                      <p className={`text-[11px] leading-relaxed mt-1 line-clamp-3 ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
                        {msg.curatedProperty.description}
                      </p>
                      <div className="flex justify-between items-baseline mt-4 pt-3 border-t border-stone-500/10">
                        <div>
                          <span className="text-[8px] uppercase tracking-wider text-stone-400">Estimated Valuation</span>
                          <span className={`font-serif text-sm font-semibold block ${isDarkCanvas ? 'text-white' : 'text-neutral-800'}`}>
                            {msg.curatedProperty.price}
                          </span>
                        </div>
                        <div className="flex gap-2">
                          <span className="text-[9px] text-[#c5a059] font-mono font-medium">
                            {msg.curatedProperty.specs.beds}B / {msg.curatedProperty.specs.baths}Ba / {msg.curatedProperty.specs.sqft} sqft
                          </span>
                        </div>
                      </div>
                    </div>
                  )}
                </div>
                <span className="text-[9px] text-stone-400 mt-1 font-mono px-1">
                  {msg.timestamp}
                </span>
              </div>
            );
          })}

          {/* Synthesis loader effect */}
          {isSynthesizing && (
            <div className="flex flex-col items-start">
              <div className={`max-w-[85%] rounded p-4 text-xs ${isDarkCanvas ? 'bg-[#2a2929]' : 'bg-[#fcf9f8]'} flex items-center gap-3 text-stone-500`}>
                <Loader2 className="w-4 h-4 animate-spin text-[#775a19]" />
                <span className="animate-pulse font-mono tracking-wide text-[11px]">
                  {synthesisSteps[loadingStep] || "Assembling architectural blueprints..."}
                </span>
              </div>
            </div>
          )}

          <div ref={endOfChatRef} />
        </div>

        {/* Action Tray: Inject Property to Main List if created */}
        {activePropertyCreated && (
          <div className={`p-4 mb-4 rounded border flex flex-col sm:flex-row items-center justify-between gap-4 animate-fade-in ${isDarkCanvas ? 'bg-[#ffdea5]/5 border-[#ffdea5]/30' : 'bg-[#775a19]/5 border-[#775a19]/20'}`}>
            <div className="text-center sm:text-left">
              <p className="text-xs font-bold font-serif">
                Inject "{activePropertyCreated.title}" into Portfolio?
              </p>
              <p className="text-[10px] text-stone-400 leading-relaxed">
                This adds the generated custom estate to your search listings and triggers the interactive floor plan view.
              </p>
            </div>
            <button
              onClick={() => handleInjectProperty(activePropertyCreated)}
              className={`px-4 py-2 rounded text-[10px] font-bold tracking-widest uppercase cursor-pointer flex items-center gap-1.5 cursor-pointer whitespace-nowrap transition-all duration-300 ${
                isDarkCanvas 
                  ? 'bg-[#ffdea5] text-black hover:bg-white' 
                  : 'bg-[#775a19] text-white hover:bg-[#c5a059]'
              }`}
            >
              <Compass strokeWidth={2} className="w-3.5 h-3.5" />
              Add to Active Portfolio
            </button>
          </div>
        )}

        {/* Input Form Prompt Box */}
        <form onSubmit={handleSubmitPrompt} className="relative">
          <input
            type="text"
            placeholder="e.g., A minimalist travertine glass pavilion facing deep Norwegian lakes..."
            value={inputPrompt}
            onChange={(e) => setInputPrompt(e.target.value)}
            disabled={isSynthesizing}
            className={`w-full p-4 pr-12 text-xs rounded border outline-none outline-0 transition-colors duration-300 font-medium ${
              isDarkCanvas 
                ? 'bg-[#1c1b1b] border-[#4e4639]/30 text-white focus:border-[#ffdea5]' 
                : 'bg-[#fcf9f8] border-[#e8ded1] text-neutral-800 focus:border-[#775a19]'
            }`}
          />
          <button
            type="submit"
            disabled={isSynthesizing || !inputPrompt.trim()}
            className={`absolute right-3 top-1/2 -translate-y-1/2 p-2 rounded-full cursor-pointer transition-colors duration-300 ${
              isSynthesizing || !inputPrompt.trim()
                ? 'text-stone-400'
                : (isDarkCanvas ? 'text-[#ffdea5] hover:text-white' : 'text-[#775a19] hover:text-[#c5a059]')
            }`}
          >
            <Send className="w-4 h-4" />
          </button>
        </form>
      </div>
    </div>
  );
}
