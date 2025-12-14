# JARVIS Command Examples

This document provides examples of commands you can use with JARVIS and the expected responses.

## Getting Started

### Basic Greetings

**Command:** "Hello JARVIS"  
**Response:** "Good day, Sir. How may I assist you?"

**Command:** "Hi"  
**Response:** "At your service, Sir. What do you need?"

**Command:** "Hey JARVIS"  
**Response:** "Greetings, Sir. All systems operational."

## Time and Date Commands

### Current Time

**Command:** "What time is it?"  
**Response:** "The current time is 10:30 AM, Sir."

**Command:** "Tell me the time"  
**Response:** "The current time is 10:30 AM, Sir."

**Command:** "Time"  
**Response:** "The current time is 10:30 AM, Sir."

### Current Date

**Command:** "What's the date?"  
**Response:** "Today is Saturday, December 14, 2025, Sir."

**Command:** "Tell me today's date"  
**Response:** "Today is Saturday, December 14, 2025, Sir."

**Command:** "What day is it?"  
**Response:** "Today is Saturday, December 14, 2025, Sir."

## System Information

### System Specifications

**Command:** "System specs"  
**Response:** "System specifications: System: Linux 6.11.0-1018-azure, Architecture: x86_64, CPU Cores: 4, CPU Usage: 0.8%, Memory: 1.49GB/15.62GB used"

**Command:** "Tell me about the system"  
**Response:** "System specifications: System: Linux 6.11.0-1018-azure, Architecture: x86_64, CPU Cores: 4, CPU Usage: 0.8%, Memory: 1.49GB/15.62GB used"

### Battery Status

**Command:** "Battery status"  
**Response:** "Battery is at 85% and currently charging, Sir."

**Command:** "How's the battery?"  
**Response:** "Battery is at 85% and currently charging, Sir."

**Command:** "Check battery"  
**Response:** "Battery is at 85% and currently charging, Sir."

### Status Check

**Command:** "Status"  
**Response:** "All systems operational, Sir. JARVIS is running at optimal capacity."

**Command:** "System status"  
**Response:** "All systems operational, Sir. JARVIS is running at optimal capacity."

**Command:** "Are you operational?"  
**Response:** "All systems operational, Sir. JARVIS is running at optimal capacity."

## Identity and Help

### Who is JARVIS?

**Command:** "Who are you?"  
**Response:** "I am JARVIS, your Just A Rather Very Intelligent System. Version 1.0.0. I am here to assist you with various tasks, control applications, provide information, and manage your digital environment."

**Command:** "What are you?"  
**Response:** "I am JARVIS, your Just A Rather Very Intelligent System. Version 1.0.0. I am here to assist you with various tasks, control applications, provide information, and manage your digital environment."

### Getting Help

**Command:** "Help"  
**Response:** Lists all available commands and capabilities

**Command:** "What can you do?"  
**Response:** Lists all available commands and capabilities

**Command:** "Show me help"  
**Response:** Lists all available commands and capabilities

## Application Control

### Opening Applications

**Command:** "Open Chrome"  
**Response:** "Opening Chrome, Sir. Note: Application control requires appropriate system permissions."

**Command:** "Launch Calculator"  
**Response:** "Opening Calculator, Sir. Note: Application control requires appropriate system permissions."

**Command:** "Open Notepad"  
**Response:** "Opening Notepad, Sir. Note: Application control requires appropriate system permissions."

## Information Queries

### Weather

**Command:** "Weather"  
**Response:** "Weather service integration would require an API key, Sir. Currently showing mock data: Clear skies, 72°F."

**Command:** "What's the weather?"  
**Response:** "Weather service integration would require an API key, Sir. Currently showing mock data: Clear skies, 72°F."

### Web Search

**Command:** "Search for Python tutorials"  
**Response:** "I would search for 'Python tutorials' for you, Sir. Web search integration is available."

**Command:** "Google artificial intelligence"  
**Response:** "I would search for 'artificial intelligence' for you, Sir. Web search integration is available."

## System Control (Simulated)

### Shutdown

**Command:** "Shutdown"  
**Response:** "System shutdown command received, Sir. This action is simulated for safety."

### Restart

**Command:** "Restart"  
**Response:** "System restart command received, Sir. This action is simulated for safety."

**Command:** "Reboot"  
**Response:** "System restart command received, Sir. This action is simulated for safety."

## Advanced Examples

### Complex Queries

**Command:** "Search for best AI frameworks"  
**Response:** "I would search for 'best AI frameworks' for you, Sir. Web search integration is available."

**Command:** "Open Visual Studio Code"  
**Response:** "Opening Visual Studio Code, Sir. Note: Application control requires appropriate system permissions."

## API Examples

### Using the REST API

#### Status Check
```bash
curl http://localhost:5000/api/status
```

**Response:**
```json
{
    "active": true,
    "name": "JARVIS",
    "timestamp": "2025-12-14T07:55:52.671620",
    "version": "1.0.0"
}
```

#### Sending Commands
```bash
curl -X POST http://localhost:5000/api/command \
  -H "Content-Type: application/json" \
  -d '{"command": "What time is it?"}'
```

**Response:**
```json
{
    "data": "07:56 AM",
    "response": "The current time is 07:56 AM, Sir.",
    "type": "time"
}
```

#### System Information
```bash
curl -X POST http://localhost:5000/api/command \
  -H "Content-Type: application/json" \
  -d '{"command": "System specs"}'
```

**Response:**
```json
{
    "data": {
        "cpu_count": 4,
        "cpu_percent": 0.8,
        "machine": "x86_64",
        "memory_total": 15.62,
        "memory_used": 1.49,
        "release": "6.11.0-1018-azure",
        "system": "Linux"
    },
    "response": "System specifications: System: Linux 6.11.0-1018-azure, Architecture: x86_64, CPU Cores: 4, CPU Usage: 0.8%, Memory: 1.49GB/15.62GB used",
    "type": "system_info"
}
```

## Tips for Best Results

1. **Be Clear**: Use clear, concise commands
2. **Be Specific**: Specify what you want (e.g., "Open Chrome" not just "Chrome")
3. **Use Natural Language**: JARVIS understands conversational commands
4. **Try Quick Buttons**: Use the UI quick command buttons for common tasks
5. **Check Response Types**: Different commands return different data types

## Command Categories

### Information
- Time, Date, Weather, System Info, Battery Status

### Control
- Open applications, System operations

### Search
- Web searches, Information lookup

### Status
- System status, JARVIS status, Help

## Extending Commands

To add new commands, modify the `process_command` method in `jarvis_bot.py`:

```python
def process_command(self, command):
    command = command.lower().strip()
    
    # Your new command
    if 'your trigger' in command:
        return self.your_function()
    
    # ... rest of commands
```

## Notes

- Some commands are simulated for safety (e.g., shutdown, restart)
- Application control requires appropriate system permissions
- Weather and advanced features can be extended with API integrations
- All responses maintain the JARVIS personality and formatting

---

**"At your service, Sir. What else can I help you with?"**
