import React, { useState } from 'react';
import { Property } from '../types';
import { Calendar, Clock, User, Mail, FileText, CheckCircle, ArrowRight } from 'lucide-react';

interface ViewingSchedulerProps {
  property: Property;
  onSuccess: () => void;
  isDarkCanvas: boolean;
}

export default function ViewingScheduler({ property, onSuccess, isDarkCanvas }: ViewingSchedulerProps) {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    date: '',
    time: '14:00',
    notes: ''
  });

  const [activeInput, setActiveInput] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [bookingConfirmed, setBookingConfirmed] = useState(false);

  const timeSlots = ["09:00", "11:00", "14:00", "16:00", "18:00"];

  const handleBookingSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.name || !formData.email || !formData.date || !formData.time) {
      alert("Please specify all details for the private viewing.");
      return;
    }

    setIsSubmitting(true);

    try {
      const res = await fetch('/api/bookings', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          propertyId: property.id,
          propertyName: property.title,
          clientName: formData.name,
          clientEmail: formData.email,
          date: formData.date,
          time: formData.time,
          notes: formData.notes
        })
      });

      if (res.ok) {
        setBookingConfirmed(true);
        // Clear form
        setFormData({ name: '', email: '', date: '', time: '14:00', notes: '' });
        setTimeout(() => {
          onSuccess(); // Close or trigger state updates in parent
        }, 3000);
      } else {
        alert("Booking submission failed. Please try again.");
      }
    } catch (err) {
      console.error(err);
      alert("Error reaching server. Doing local simulation booking.");
      setBookingConfirmed(true);
      setTimeout(() => {
        onSuccess();
      }, 3000);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className={`p-6 rounded-lg ${isDarkCanvas ? 'bg-[#1e1e1e] border border-[#4e4639]/20' : 'bg-white border border-[#e8ded1]/30'} shadow-md transition-all duration-500`}>
      {bookingConfirmed ? (
        <div className="py-8 text-center flex flex-col items-center justify-center">
          <div className={`w-12 h-12 rounded-full ${isDarkCanvas ? 'bg-[#ffdea5]/10 text-[#ffdea5]' : 'bg-[#775a19]/10 text-[#775a19]'} flex items-center justify-center mb-4`}>
            <CheckCircle strokeWidth={1} className="w-8 h-8 animate-bounce" />
          </div>
          <span className={`text-[9px] tracking-[0.25em] uppercase font-bold ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
            CONFIRMATION RECORD
          </span>
          <h4 className={`font-serif text-2xl font-bold mt-2 ${isDarkCanvas ? 'text-white' : 'text-neutral-900'}`}>
            Tour Requested Successfully
          </h4>
          <p className={`text-xs mt-3 max-w-sm mx-auto leading-relaxed ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
            Our Private Client Desk has logged your dossier. <strong>{property.agent.name}</strong> will contact you via email shortly to coordinate travel.
          </p>
          <div className={`mt-6 p-4 rounded text-left border text-xs max-w-xs w-full ${isDarkCanvas ? 'bg-stone-800 border-[#4e4639]/20' : 'bg-[#fcf9f8] border-[#e8ded1]/60'}`}>
            <p className="font-serif font-bold text-sm mb-1">{property.title}</p>
            <p className="font-mono text-stone-500 mb-2">{property.location}</p>
            <div className="space-y-1 text-stone-400 font-medium">
              <p>Host Agent: <strong className={isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}>{property.agent.name}</strong></p>
              <p>Est. Date: {formData.date || new Date().toISOString().split('T')[0]}</p>
              <p>Est. Time: {formData.time}</p>
            </div>
          </div>
        </div>
      ) : (
        <form onSubmit={handleBookingSubmit} className="space-y-6">
          <div>
            <span className={`text-[9px] tracking-widest uppercase font-semibold block ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
              PREMIUM ARRANGEMENTS
            </span>
            <h4 className={`font-serif text-xl font-bold mt-0.5 ${isDarkCanvas ? 'text-white' : 'text-neutral-900'}`}>
              Schedule a Private Viewing
            </h4>
            <p className={`text-xs mt-1 leading-relaxed ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-500'}`}>
              Tour <strong>{property.title}</strong> accompanied exclusively by our Principal Client Partners.
            </p>
          </div>

          {/* Floating Minimal Input Name */}
          <div className="relative pt-4">
            <input
              type="text"
              name="name"
              placeholder=" "
              value={formData.name}
              onFocus={() => setActiveInput('name')}
              onBlur={() => setActiveInput(null)}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className={`floating-input w-full bg-transparent border-b-2 py-2 pr-4 outline-none text-xs transition-colors duration-300 font-medium ${
                activeInput === 'name' 
                  ? (isDarkCanvas ? 'border-[#ffdea5] text-white' : 'border-[#775a19] text-neutral-950')
                  : (isDarkCanvas ? 'border-[#4e4639]/30 text-stone-300' : 'border-secondary-container text-neutral-800')
              }`}
              required
            />
            <label className={`floating-label absolute left-0 bottom-2 text-xs tracking-wider transition-all duration-300 pointer-events-none uppercase font-semibold ${
              activeInput === 'name' 
                ? (isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]') 
                : 'text-neutral-400'
            }`}>
              Full Name
            </label>
          </div>

          {/* Floating Minimal Input Email */}
          <div className="relative pt-4">
            <input
              type="email"
              name="email"
              placeholder=" "
              value={formData.email}
              onFocus={() => setActiveInput('email')}
              onBlur={() => setActiveInput(null)}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              className={`floating-input w-full bg-transparent border-b-2 py-2 pr-4 outline-none text-xs transition-colors duration-300 font-medium ${
                activeInput === 'email' 
                  ? (isDarkCanvas ? 'border-[#ffdea5] text-white' : 'border-[#775a19] text-neutral-950')
                  : (isDarkCanvas ? 'border-[#4e4639]/30 text-stone-300' : 'border-secondary-container text-neutral-800')
              }`}
              required
            />
            <label className={`floating-label absolute left-0 bottom-2 text-xs tracking-wider transition-all duration-300 pointer-events-none uppercase font-semibold ${
              activeInput === 'email' 
                ? (isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]') 
                : 'text-neutral-400'
            }`}>
              Email Address
            </label>
          </div>

          {/* Floating Minimal Input Date */}
          <div className="relative pt-4">
            <input
              type="date"
              name="date"
              placeholder=" "
              value={formData.date}
              onFocus={() => setActiveInput('date')}
              onBlur={() => setActiveInput(null)}
              onChange={(e) => setFormData({ ...formData, date: e.target.value })}
              className={`floating-input w-full bg-transparent border-b-2 py-2 outline-none text-xs transition-colors duration-300 font-medium ${
                activeInput === 'date' 
                  ? (isDarkCanvas ? 'border-[#ffdea5] text-white animate-pulse' : 'border-[#775a19] text-neutral-950')
                  : (isDarkCanvas ? 'border-[#4e4639]/30 text-stone-300' : 'border-secondary-container text-neutral-800')
              }`}
              required
            />
            <label className={`floating-label absolute left-0 bottom-2 text-xs tracking-wider transition-all duration-300 pointer-events-none uppercase font-semibold ${
              formData.date ? 'translate-y-[-1.25rem] scale-85 text-[#775a19]' : 'text-neutral-400'
            }`}>
              Preferred Viewing Date
            </label>
          </div>

          {/* Preferred Time Grid */}
          <div>
            <label className="text-[10px] tracking-wider uppercase font-semibold text-neutral-400 block mb-2">
              Preferred Viewing Time
            </label>
            <div className="grid grid-cols-5 gap-1.5 matches-touch-targets">
              {timeSlots.map((time) => {
                const isSelected = formData.time === time;
                return (
                  <button
                    key={time}
                    type="button"
                    onClick={() => setFormData({ ...formData, time })}
                    className={`py-2 text-center text-xs rounded transition-all duration-300 font-mono cursor-pointer ${
                      isSelected
                        ? (isDarkCanvas ? 'bg-[#ffdea5] text-black font-bold' : 'bg-[#775a19] text-white font-bold')
                        : (isDarkCanvas ? 'bg-neutral-800 hover:bg-neutral-700 text-stone-300 border border-transparent' : 'bg-stone-50 hover:bg-stone-100 text-neutral-700 border border-secondary-container')
                    }`}
                  >
                    {time}
                  </button>
                );
              })}
            </div>
          </div>

          {/* Floating Minimal Input Notes */}
          <div className="relative pt-4">
            <textarea
              name="notes"
              placeholder=" "
              rows={2}
              value={formData.notes}
              onFocus={() => setActiveInput('notes')}
              onBlur={() => setActiveInput(null)}
              onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
              className={`floating-input w-full bg-transparent border-b-2 py-2 outline-none text-xs transition-colors duration-300 resize-none font-medium ${
                activeInput === 'notes' 
                  ? (isDarkCanvas ? 'border-[#ffdea5] text-white' : 'border-[#775a19] text-neutral-950')
                  : (isDarkCanvas ? 'border-[#4e4639]/30 text-stone-300' : 'border-secondary-container text-neutral-800')
              }`}
            />
            <label className={`floating-label absolute left-0 bottom-6 text-xs tracking-wider transition-all duration-300 pointer-events-none uppercase font-semibold ${
              activeInput === 'notes' 
                ? (isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]') 
                : 'text-neutral-400'
            }`}>
              Special Client Requests / Security Clearances
            </label>
          </div>

          <button
            type="submit"
            disabled={isSubmitting}
            className={`w-full py-3 rounded text-[10px] font-bold tracking-widest uppercase cursor-pointer transition-all duration-500 border flex items-center justify-center gap-2 shadow-sm ${
              isDarkCanvas 
                ? 'bg-[#ffdea5] text-black border-transparent hover:bg-white hover:shadow-[0_12px_24px_rgba(255,255,255,0.05)]' 
                : 'bg-[#775a19] text-white border-transparent hover:bg-[#c5a059] hover:-translate-y-0.5 hover:shadow-[0_12px_24px_rgba(119,90,25,0.15)]'
            }`}
          >
            {isSubmitting ? "Submitting Portfolio Request..." : "Request Private Courier & Touring"}
            <ArrowRight strokeWidth={2} className="w-3.5 h-3.5" />
          </button>
        </form>
      )}
    </div>
  );
}
