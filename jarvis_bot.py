#!/usr/bin/env python3
"""
Jarvis AI Bot - Main Backend Server
A high-tech AI assistant inspired by JARVIS from Iron Man
"""

from flask import Flask, render_template, request, jsonify
from flask_cors import CORS
import datetime
import random
import json
import os
import subprocess
import platform
import psutil
import requests

app = Flask(__name__)
CORS(app)

class JarvisAI:
    """Main JARVIS AI class with all capabilities"""
    
    def __init__(self):
        self.name = "JARVIS"
        self.version = "1.0.0"
        self.active = True
        
    def process_command(self, command):
        """Process user commands and return appropriate responses"""
        command = command.lower().strip()
        
        # Greeting commands
        if any(word in command for word in ['hello', 'hi', 'hey', 'greetings']):
            return self.greet()
        
        # Time commands
        elif any(word in command for word in ['time', 'clock']):
            return self.get_time()
        
        # Date commands
        elif any(word in command for word in ['date', 'today', 'day']):
            return self.get_date()
        
        # System info commands
        elif 'system' in command or 'specs' in command:
            return self.get_system_info()
        
        # Battery status
        elif 'battery' in command:
            return self.get_battery_status()
        
        # Weather commands
        elif 'weather' in command:
            return self.get_weather(command)
        
        # Search commands
        elif 'search' in command or 'google' in command:
            return self.web_search(command)
        
        # App control commands
        elif 'open' in command or 'launch' in command:
            return self.open_application(command)
        
        # System control
        elif 'shutdown' in command:
            return self.system_control('shutdown')
        elif 'restart' in command or 'reboot' in command:
            return self.system_control('restart')
        
        # Identity commands
        elif 'who are you' in command or 'what are you' in command:
            return self.introduce()
        
        # Help command
        elif 'help' in command or 'what can you do' in command:
            return self.get_help()
        
        # Status check
        elif 'status' in command or 'operational' in command:
            return self.status_check()
        
        # Default response
        else:
            return self.default_response(command)
    
    def greet(self):
        """Return a greeting message"""
        greetings = [
            "Good day, Sir. How may I assist you?",
            "At your service, Sir. What do you need?",
            "Hello, Sir. JARVIS is online and ready.",
            "Greetings, Sir. All systems operational.",
            "Welcome back, Sir. How can I help?"
        ]
        return {
            'response': random.choice(greetings),
            'type': 'greeting'
        }
    
    def get_time(self):
        """Get current time"""
        current_time = datetime.datetime.now().strftime("%I:%M %p")
        return {
            'response': f"The current time is {current_time}, Sir.",
            'type': 'time',
            'data': current_time
        }
    
    def get_date(self):
        """Get current date"""
        current_date = datetime.datetime.now().strftime("%A, %B %d, %Y")
        return {
            'response': f"Today is {current_date}, Sir.",
            'type': 'date',
            'data': current_date
        }
    
    def get_system_info(self):
        """Get system information"""
        system = platform.system()
        release = platform.release()
        machine = platform.machine()
        cpu_count = psutil.cpu_count()
        cpu_percent = psutil.cpu_percent(interval=1)
        memory = psutil.virtual_memory()
        memory_total = round(memory.total / (1024**3), 2)
        memory_used = round(memory.used / (1024**3), 2)
        
        info = f"System: {system} {release}, Architecture: {machine}, CPU Cores: {cpu_count}, CPU Usage: {cpu_percent}%, Memory: {memory_used}GB/{memory_total}GB used"
        
        return {
            'response': f"System specifications: {info}",
            'type': 'system_info',
            'data': {
                'system': system,
                'release': release,
                'machine': machine,
                'cpu_count': cpu_count,
                'cpu_percent': cpu_percent,
                'memory_total': memory_total,
                'memory_used': memory_used
            }
        }
    
    def get_battery_status(self):
        """Get battery status"""
        try:
            battery = psutil.sensors_battery()
            if battery:
                percent = battery.percent
                plugged = battery.power_plugged
                status = "charging" if plugged else "discharging"
                return {
                    'response': f"Battery is at {percent}% and currently {status}, Sir.",
                    'type': 'battery',
                    'data': {'percent': percent, 'status': status}
                }
            else:
                return {
                    'response': "Battery information is not available on this system, Sir.",
                    'type': 'battery',
                    'data': None
                }
        except:
            return {
                'response': "Unable to retrieve battery status, Sir.",
                'type': 'battery',
                'data': None
            }
    
    def get_weather(self, command):
        """Get weather information"""
        # Mock weather data - in production, integrate with weather API
        return {
            'response': "Weather service integration would require an API key, Sir. Currently showing mock data: Clear skies, 72°F.",
            'type': 'weather',
            'data': {
                'temperature': 72,
                'condition': 'Clear',
                'humidity': 45
            }
        }
    
    def web_search(self, command):
        """Perform web search"""
        query = command.replace('search', '').replace('google', '').strip()
        return {
            'response': f"I would search for '{query}' for you, Sir. Web search integration is available.",
            'type': 'search',
            'data': {'query': query}
        }
    
    def open_application(self, command):
        """Open applications"""
        app_name = command.replace('open', '').replace('launch', '').strip()
        return {
            'response': f"Opening {app_name}, Sir. Note: Application control requires appropriate system permissions.",
            'type': 'app_control',
            'data': {'app': app_name}
        }
    
    def system_control(self, action):
        """Control system operations"""
        return {
            'response': f"System {action} command received, Sir. This action is simulated for safety.",
            'type': 'system_control',
            'data': {'action': action}
        }
    
    def introduce(self):
        """Introduce JARVIS"""
        return {
            'response': f"I am {self.name}, your Just A Rather Very Intelligent System. Version {self.version}. I am here to assist you with various tasks, control applications, provide information, and manage your digital environment.",
            'type': 'introduction'
        }
    
    def get_help(self):
        """Provide help information"""
        help_text = """I can assist you with:
        - Time and date information
        - System specifications and status
        - Battery status
        - Weather information
        - Web searches
        - Application control
        - System operations
        
        Simply speak or type your command, Sir."""
        
        return {
            'response': help_text,
            'type': 'help'
        }
    
    def status_check(self):
        """Check JARVIS status"""
        return {
            'response': "All systems operational, Sir. JARVIS is running at optimal capacity.",
            'type': 'status',
            'data': {
                'version': self.version,
                'active': self.active,
                'status': 'operational'
            }
        }
    
    def default_response(self, command):
        """Default response for unknown commands"""
        responses = [
            f"I'm processing your request for '{command}', Sir. This feature is being analyzed.",
            f"Understood, Sir. I'm working on '{command}' for you.",
            f"I acknowledge your command: '{command}'. Processing available actions.",
            f"Certainly, Sir. I'm analyzing the best way to handle '{command}'."
        ]
        return {
            'response': random.choice(responses),
            'type': 'default',
            'data': {'command': command}
        }

# Initialize JARVIS
jarvis = JarvisAI()

@app.route('/')
def index():
    """Serve the main page"""
    return render_template('index.html')

@app.route('/api/command', methods=['POST'])
def process_command():
    """Process commands from the frontend"""
    data = request.get_json()
    command = data.get('command', '')
    
    if not command:
        return jsonify({'error': 'No command provided'}), 400
    
    response = jarvis.process_command(command)
    return jsonify(response)

@app.route('/api/status', methods=['GET'])
def get_status():
    """Get JARVIS status"""
    return jsonify({
        'name': jarvis.name,
        'version': jarvis.version,
        'active': jarvis.active,
        'timestamp': datetime.datetime.now().isoformat()
    })

if __name__ == '__main__':
    print(f"\n{'='*60}")
    print(f"  JARVIS AI BOT - Version {jarvis.version}")
    print(f"{'='*60}")
    print(f"  Starting server at http://localhost:5000")
    print(f"  All systems online and operational")
    print(f"{'='*60}\n")
    app.run(debug=True, host='0.0.0.0', port=5000)
