# Joubot

**Joubot** is a Telegram bot that searches for job vacancies containing specific keywords and returns them via a command. The ultimate goal is for it to run completely autonomously in the future.

## Project Scope

### Objective
Build an automated system that finds job vacancies based on user-selected keywords and publishes them to a Telegram bot.

### Features
- Job vacancy collection (web scraping)
- Keyword-based filtering
- Telegram bot integration
- Database storage for vacancies
- Automatic notifications
- Time-based scheduling

### Parameters
- **Keyword** (e.g., job type, programming language, work model, etc.)
- **Time interval** (frequency of searches, in hours)
- **Target websites** (platforms where vacancies will be searched)

---

## Development Roadmap (4 Sprints – 1 week each)

| Sprint | Deliverables |
| :---: | :--- |
| **Sprint 1** | Maven project configured. <br> `Job` class created. <br> Telegram bot responding to basic commands. <br> Basic web scraping implemented. | DONE
| **Sprint 2** | Keyword filter logic implemented. <br> H2 database configured and integrated. <br> `/vagas` (jobs) command fully functional. |
| **Sprint 3** | Automatic scheduling implemented. <br> Automatic notification system working. <br> Basic error handling added. |
| **Sprint 4** | Integration with an additional job site. <br> Code refinements and optimizations. <br> Testing and bug fixes. <br> Comprehensive documentation and `README.md` completed. |
