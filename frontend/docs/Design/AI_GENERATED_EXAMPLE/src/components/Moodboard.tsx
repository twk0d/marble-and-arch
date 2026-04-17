import { useState } from 'react';
import { Property, MoodboardItem } from '../types';
import { Heart, FileText, Trash2, Plus, CornerDownRight, Sparkles, AlertCircle } from 'lucide-react';

interface MoodboardProps {
  savedProperties: Property[];
  onRemoveSave: (id: string) => void;
  isDarkCanvas: boolean;
}

export default function Moodboard({ savedProperties, onRemoveSave, isDarkCanvas }: MoodboardProps) {
  // Local state for customized comments per saved property
  const [designerNotes, setDesignerNotes] = useState<Record<string, string>>({
    'estate-1': "Planning structural limestone columns. Ideal cliff convergence.",
    'estate-2': "Excellent basalt thermal loop. Need star-watching deck orientation focus."
  });

  const [activeNoteText, setActiveNoteText] = useState('');
  const [editingPropertyId, setEditingPropertyId] = useState<string | null>(null);
  
  // State for exhibiting the final generated Briefing dossier
  const [showBriefDossier, setShowBriefDossier] = useState(false);

  const handleSaveNote = (propertyId: string) => {
    if (!activeNoteText.trim()) return;
    setDesignerNotes(prev => ({
      ...prev,
      [propertyId]: activeNoteText
    }));
    setActiveNoteText('');
    setEditingPropertyId(null);
  };

  const calculateTotalValuation = () => {
    const total = savedProperties.reduce((sum, prop) => sum + prop.rawPrice, 0);
    return `$${total.toLocaleString()}`;
  };

  return (
    <div className="space-y-8">
      {showBriefDossier ? (
        // Spectacular Dossier Sheet Presentation Component
        <div className={`p-8 md:p-12 rounded-lg border ${
          isDarkCanvas 
            ? 'bg-[#1a1919] border-[#ca9e5a]/40 text-white shadow-[0_40px_80px_rgba(255,255,255,0.02)]' 
            : 'bg-white border-[#775a19]/30 text-neutral-900 shadow-[0_40px_80px_rgba(119,90,25,0.05)]'
        } max-w-4xl mx-auto space-y-8 relative animate-fade-in`}>
          
          {/* Close Action */}
          <button 
            onClick={() => setShowBriefDossier(false)}
            className={`absolute top-6 right-6 text-xs uppercase tracking-widest font-semibold py-2 px-4 shadow border rounded-sm hover:-translate-y-0.5 cursor-pointer transition-all ${
              isDarkCanvas ? 'bg-stone-800 border-stone-700 text-[#ffdea5] hover:bg-stone-700' : 'bg-[#fcf9f8] border-stone-200 text-[#775a19] hover:bg-stone-100'
            }`}
          >
            ← Exit Dossier
          </button>

          {/* Letterhead Header */}
          <div className="text-center border-b pb-8 space-y-2">
            <span className={`text-[9px] tracking-[0.4em] font-bold ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
              MARBLE &amp; ARCH DESIGN ASSOCIATES
            </span>
            <h2 className="font-serif text-3xl font-bold uppercase tracking-widest">
              Architectural Brief Dossier
            </h2>
            <p className="text-xs text-stone-400 font-mono">
              CONFIDENTIAL PORTFOLIO SPECIFICATIONS // GUEST IDENTIFIER: twkod@pm.me // DATE: {new Date().toLocaleDateString()}
            </p>
          </div>

          {/* Summary KPIs */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 text-center border-b pb-8">
            <div className="space-y-1">
              <span className="text-[8px] text-stone-400 uppercase tracking-widest block">Dossier Items</span>
              <span className="text-2xl font-serif font-bold">{savedProperties.length} Estates</span>
            </div>
            <div className="space-y-1 border-y md:border-y-0 md:border-x py-4 md:py-0">
              <span className="text-[8px] text-stone-400 uppercase tracking-widest block">Combined Value</span>
              <span className="text-2xl font-serif font-bold text-[#c5a059]">{calculateTotalValuation()}</span>
            </div>
            <div className="space-y-1">
              <span className="text-[8px] text-stone-400 uppercase tracking-widest block">Estimated Timeline</span>
              <span className="text-2xl font-serif font-bold">14 - 18 Months</span>
            </div>
          </div>

          {/* Detailed Property Sheets */}
          <div className="space-y-8">
            {savedProperties.map((prop, idx) => (
              <div key={prop.id} className="grid grid-cols-1 md:grid-cols-4 gap-6 items-start border-b pb-6 last:border-b-0 last:pb-0">
                <div className="md:col-span-1 space-y-2">
                  <span className="font-mono text-xs opacity-45 block">Item No. 0{idx + 1}</span>
                  <h4 className="font-serif text-lg font-bold">{prop.title}</h4>
                  <p className="text-[9px] text-[#c5a059] uppercase tracking-widest font-mono">{prop.location}</p>
                  <p className="text-xs font-serif font-semibold">{prop.price}</p>
                </div>

                <div className="md:col-span-3 space-y-4">
                  <div>
                    <span className="text-[9px] uppercase tracking-widest text-stone-400 font-semibold block mb-1">
                      Architectural Summary
                    </span>
                    <p className="text-xs leading-relaxed text-stone-400 font-medium">{prop.description}</p>
                  </div>

                  {/* Designer notes if present */}
                  {designerNotes[prop.id] && (
                    <div className={`p-4 rounded border ${isDarkCanvas ? 'bg-stone-800 border-stone-700/50' : 'bg-stone-50 border-[#e8ded1]/50'}`}>
                      <span className="text-[8px] font-bold uppercase tracking-wider text-[#c5a059] block mb-1">
                        Curator / Client Notes
                      </span>
                      <p className="text-xs italic leading-relaxed text-stone-400">{designerNotes[prop.id]}</p>
                    </div>
                  )}

                  {/* Materials */}
                  <div>
                    <span className="text-[9px] uppercase tracking-widest text-stone-400 block mb-1.5">Specified Slabs &amp; Finishing Slabs</span>
                    <div className="flex flex-wrap gap-1">
                      {prop.specs.materials.map(mat => (
                        <span key={mat} className="text-[8px] font-semibold uppercase tracking-wider px-2 py-0.5 bg-stone-500/10 rounded-sm">
                          {mat}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Footer Statement */}
          <div className="text-center pt-8 border-t text-[10px] text-stone-500 leading-relaxed max-w-lg mx-auto">
            <p>
              This document serves as an actionable architectural framework. Submissions to building permissions and landscape consultants will align directly with these parameters.
            </p>
            <p className="font-semibold text-[#c5a059] mt-2 tracking-widest uppercase">
              MARBLE &amp; ARCH DESIGN OFFICE
            </p>
          </div>
        </div>
      ) : (
        // Standard Grid Board
        <div className="space-y-6">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <div>
              <span className={`text-[9px] tracking-widest uppercase font-bold ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
                CURATOR STUDIO
              </span>
              <h2 className={`font-serif text-2xl font-bold mt-0.5 ${isDarkCanvas ? 'text-white' : 'text-neutral-900'}`}>
                Visual Moodboard Workspace
              </h2>
              <p className={`text-xs mt-1.5 ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-500'}`}>
                Collect favorite estates, attach material annotations, and synthesize customized briefs.
              </p>
            </div>

            {savedProperties.length > 0 && (
              <button
                onClick={() => setShowBriefDossier(true)}
                className={`py-2.5 px-5 rounded text-[10px] font-bold tracking-widest uppercase cursor-pointer transition-all duration-300 border shadow-xs ${
                  isDarkCanvas 
                    ? 'bg-[#ffdea5] text-black border-transparent hover:bg-white' 
                    : 'bg-[#775a19] text-white border-transparent hover:bg-[#c5a059] hover:-translate-y-0.5'
                }`}
              >
                Synthesize Architectural Brief Brief
              </button>
            )}
          </div>

          {savedProperties.length === 0 ? (
            <div className={`p-16 text-center rounded border-2 border-dashed flex flex-col items-center justify-center ${
              isDarkCanvas ? 'border-stone-700 bg-stone-900/40 text-stone-400' : 'border-[#e8ded1]/80 bg-[#fcf9f8] text-stone-500'
            }`}>
              <Heart strokeWidth={1} className="w-12 h-12 mb-4 text-stone-400" />
              <h4 className="font-serif text-lg font-bold">Your moodboard is vacant</h4>
              <p className="text-xs text-stone-400 max-w-sm mt-2 leading-relaxed">
                Explore our classical and modernist estate catalogs, and select the small heart marker on any project to pin it here for curation.
              </p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              {savedProperties.map((prop) => (
                <div 
                  key={prop.id}
                  className={`p-6 rounded-lg border flex flex-col justify-between items-stretch ${
                    isDarkCanvas 
                      ? 'bg-[#1e1e1e] border-stone-800 text-white' 
                      : 'bg-white border-[#e8ded1]/30 text-neutral-900'
                  }`}
                >
                  <div className="space-y-4">
                    {/* Header line with thumbnail */}
                    <div className="flex gap-4 items-center">
                      <div className="w-16 h-16 rounded overflow-hidden shrink-0 bg-stone-100">
                        <img 
                          src={prop.imageUrl} 
                          alt={prop.title}
                          referrerPolicy="no-referrer"
                          className="w-full h-full object-cover"
                        />
                      </div>
                      <div>
                        <span className="text-[9px] text-[#c5a059] uppercase tracking-wider block font-mono">
                          {prop.location}
                        </span>
                        <h4 className="font-serif text-base font-bold">
                          {prop.title}
                        </h4>
                        <span className="text-xs font-serif font-semibold text-stone-400">
                          {prop.price}
                        </span>
                      </div>
                    </div>

                    {/* Active Curator Annotation */}
                    <div className={`p-4 rounded border ${
                      isDarkCanvas ? 'bg-[#2a2929] border-stone-800' : 'bg-[#fcf9f8] border-stone-200/50'
                    }`}>
                      <span className="text-[8px] font-bold tracking-wider uppercase text-neutral-400 block mb-1">
                        Active Material &amp; Layout Annotations
                      </span>
                      {designerNotes[prop.id] ? (
                        <div className="flex justify-between items-start gap-3">
                          <p className="text-xs italic leading-relaxed text-stone-500">
                            "{designerNotes[prop.id]}"
                          </p>
                          <button
                            onClick={() => {
                              setEditingPropertyId(prop.id);
                              setActiveNoteText(designerNotes[prop.id]);
                            }}
                            className="text-[9px] uppercase font-semibold text-[#c5a059] hover:underline shrink-0"
                          >
                            Edit
                          </button>
                        </div>
                      ) : (
                        <button
                          onClick={() => setEditingPropertyId(prop.id)}
                          className={`text-xs flex items-center gap-1.5 py-1.5 px-3 rounded border border-dashed hover:border-solid hover:bg-stone-100 cursor-pointer ${
                            isDarkCanvas ? 'border-stone-700 hover:bg-stone-800 text-stone-300' : 'border-stone-300 text-neutral-600'
                          }`}
                        >
                          <Plus strokeWidth={2} className="w-3.5 h-3.5" />
                          Add Curator Note
                        </button>
                      )}
                    </div>

                    {/* Annotation Edit Window */}
                    {editingPropertyId === prop.id && (
                      <div className="space-y-3 p-3 rounded border border-[#c5a059]/40 bg-stone-500/5 matches-touch-targets">
                        <textarea
                          placeholder="e.g. Concrete pool needs extra rock support. Trafficking glass lines from master bedroom..."
                          value={activeNoteText}
                          onChange={(e) => setActiveNoteText(e.target.value)}
                          className="w-full bg-transparent outline-none text-xs p-1"
                        />
                        <div className="flex justify-end gap-2 text-[10px] uppercase font-bold tracking-wider">
                          <button 
                            onClick={() => {
                              setEditingPropertyId(null);
                              setActiveNoteText('');
                            }}
                            className="px-2 py-1 text-stone-400"
                          >
                            Cancel
                          </button>
                          <button 
                            onClick={() => handleSaveNote(prop.id)}
                            className="px-3 py-1 bg-[#775a19] text-white rounded-sm"
                          >
                            Save Note
                          </button>
                        </div>
                      </div>
                    )}
                  </div>

                  {/* Removal Action */}
                  <div className="mt-6 pt-4 border-t border-stone-500/10 flex justify-between items-center text-xs">
                    <span className="text-[10px] text-stone-400">
                      Added {new Date().toLocaleDateString()}
                    </span>
                    <button
                      onClick={() => onRemoveSave(prop.id)}
                      className="text-stone-400 hover:text-red-500 transition-colors flex items-center gap-1 cursor-pointer"
                      title="De-escalate item from moodboard"
                    >
                      <Trash2 strokeWidth={1.5} className="w-3.5 h-3.5" />
                      <span className="text-[9px] uppercase tracking-wider font-semibold">Remove</span>
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
}
