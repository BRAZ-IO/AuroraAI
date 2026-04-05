# 📚 AuroraDocs-Web

*AI-Powered Documentation Generator for Modern Development*

---

## 🚀 About AuroraDocs-Web

AuroraDocs-Web is a cutting-edge web-based platform designed to revolutionize how developers create project documentation, README files, and AI prompts. Inspired by tools like Windsurf, this browser-based solution leverages advanced AI to generate comprehensive, professional documentation in seconds.

Transform your codebase into beautifully structured documentation with an intuitive interface that adapts to your project's unique architecture and requirements.

---

## 📊 Current Implementation Status

### ✅ **Frontend** (Planning Phase)
- [ ] React.js application setup
- [ ] TypeScript configuration
- [ ] Tailwind CSS styling
- [ ] Component library integration
- [ ] Real-time preview interface
- [ ] Markdown editor with syntax highlighting

### ✅ **Backend** (Architecture Phase)
- [x] Spring Boot 3.2.5 project structure
- [x] Maven configuration with Java 17
- [x] REST API architecture skeleton
- [x] Entity-Repository-Service-Controller pattern
- [x] H2 Database for development
- [ ] API endpoint implementation
- [ ] Authentication & Authorization
- [ ] File upload/processing

### ⏳ **AI Integration** (Planning Phase)
- [ ] OpenAI GPT-4 integration
- [ ] LangChain framework setup
- [ ] Prompt engineering templates
- [ ] Context-aware generation
- [ ] Custom model fine-tuning
- [ ] Vector database for RAG

### ⏳ **Database** (Design Phase)
- [ ] PostgreSQL for production
- [ ] Flyway migrations
- [ ] Redis caching layer
- [ ] Document storage schema
- [ ] User project management
- [ ] Generation history tracking

---

## 🏗️ Architecture Diagram

```
AuroraDocs-Web/
├── 📂 client/                    # Frontend React Application
│   ├── 📂 src/
│   │   ├── 📂 components/        # UI Components
│   │   │   ├── 📄 Editor.tsx     # Markdown editor
│   │   │   ├── 📄 Preview.tsx    # Live preview
│   │   │   ├── 📄 Generator.tsx  # AI generation panel
│   │   │   └── 📄 Templates.tsx  # Template selector
│   │   ├── 📂 pages/            # Application pages
│   │   │   ├── � Dashboard.tsx
│   │   │   ├── 📄 Generator.tsx
│   │   │   └── � History.tsx
│   │   ├── 📂 services/         # API services
│   │   │   └── � api.ts        # Axios/Fetch wrapper
│   │   ├── 📂 hooks/            # Custom React hooks
│   │   └── 📂 utils/            # Utility functions
│   └── 📄 package.json
├── 📂 backend/                   # Backend Spring Boot Application
│   ├── 📂 src/
│   │   ├── 📂 main/
│   │   │   ├── 📂 java/
│   │   │   │   └── 📂 com/auroradocs/
│   │   │   │       ├── 📄 DocsApplication.java
│   │   │   │       ├── 📂 controller/
│   │   │   │       │   ├── 📄 DocumentationController.java
│   │   │   │       │   ├── � GenerationController.java
│   │   │   │       │   └── 📄 TemplateController.java
│   │   │   │       ├── 📂 service/
│   │   │   │       │   ├── 📄 DocumentationService.java
│   │   │   │       │   ├── 📄 GenerationService.java
│   │   │   │       │   └── 📄 AIService.java
│   │   │   │       ├── 📂 repository/
│   │   │   │       │   ├── 📄 DocumentationRepository.java
│   │   │   │       │   └── 📄 TemplateRepository.java
│   │   │   │       ├── 📂 entity/
│   │   │   │       │   ├── 📄 Documentation.java
│   │   │   │       │   ├── 📄 Template.java
│   │   │   │       │   └── 📄 GenerationHistory.java
│   │   │   │       ├── 📂 dto/
│   │   │   │       │   ├── 📄 GenerationRequest.java
│   │   │   │       │   └── 📄 GenerationResponse.java
│   │   │   │       ├── 📂 config/
│   │   │   │       │   ├── 📄 AIConfig.java
│   │   │   │       │   └── 📄 WebConfig.java
│   │   │   │       └── 📂 exception/
│   │   │   │           └── 📄 GenerationException.java
│   │   │   └── 📂 resources/
│   │   │       ├── 📄 application.yml
│   │   │       └── 📂 db/migration/
│   │   └── 📂 test/
│   └── 📄 pom.xml
├── 📂 ai/                        # AI/ML Components
│   ├── 📂 prompts/               # Prompt templates
│   ├── 📂 models/                # Custom models
│   └── 📂 pipelines/             # Processing pipelines
└── � docker-compose.yml
```

---

## ✨ Features

### 🎯 **MVP Features (Current Focus)**
- [x] Project architecture setup
- [x] Spring Boot backend skeleton
- [ ] Code repository analysis
- [ ] Automatic README generation
- [ ] Template-based documentation
- [ ] Markdown export functionality
- [ ] Basic AI integration
- [ ] User authentication
- [ ] Project history tracking

### � **Planned Features**
- [ ] Multi-format documentation (MD, HTML, PDF)
- [ ] Custom prompt engineering
- [ ] Integration with GitHub/GitLab
- [ ] Real-time collaboration
- [ ] Version control for documentation
- [ ] Analytics and insights
- [ ] API for third-party integrations
- [ ] Mobile-responsive design
- [ ] Dark mode support
- [ ] Multi-language support

---

## 🛠️ Tech Stack

### **Frontend**
- **React.js 18** - Modern UI framework
- **TypeScript** - Type-safe development
- **Tailwind CSS** - Utility-first styling
- **Monaco Editor** - Code editing with syntax highlighting
- **React Markdown** - Markdown rendering
- **Vite** - Fast build tool
- **Axios** - HTTP client

### **Backend**
- **Spring Boot 3.2.5** - Java application framework
- **Java 17** - Modern Java development
- **Spring Data JPA** - Database abstraction
- **Spring Security** - Authentication & authorization
- **H2 Database** - Development database
- **PostgreSQL** - Production database
- **Maven** - Dependency management
- **Docker** - Containerization

### **AI Integration**
- **OpenAI GPT-4** - Primary language model
- **LangChain** - AI framework
- **Vector Database** - Context search (Pinecone/Weaviate)
- **Custom Prompts** - Specialized templates

### **DevOps**
- **Docker Compose** - Local development
- **GitHub Actions** - CI/CD pipeline
- **Nginx** - Reverse proxy
- **Redis** - Caching layer

---

## 🗺️ Roadmap

### 🚀 **Short-term** (Q2 2024)
- [ ] Complete backend API implementation
- [ ] Integrate OpenAI API
- [ ] Build frontend React application
- [ ] Implement authentication system
- [ ] Create documentation templates
- [ ] Add basic generation features

### 🎯 **Mid-term** (Q3 2024)
- [ ] GitHub/GitLab integration
- [ ] Advanced prompt engineering
- [ ] Real-time collaboration
- [ ] Version control for docs
- [ ] Performance optimization
- [ ] Mobile responsive design

### 🔮 **Long-term** (Q4 2024+)
- [ ] Multi-language support
- [ ] Enterprise features
- [ ] API marketplace
- [ ] Custom model training
- [ ] Analytics dashboard
- [ ] Global deployment

---

## 📦 Installation & Setup

### Prerequisites
- Java 17+
- Maven 3.8+
- Node.js 18+
- PostgreSQL 14+ (production)
- Docker (optional)
- OpenAI API Key

### Quick Start

```bash
# Clone the repository
git clone https://github.com/your-org/AuroraDocs-Web.git
cd AuroraDocs-Web

# Backend Setup
cd backend
cp .env.example .env
# Edit .env with your OpenAI API key and database credentials
mvn clean install
mvn spring-boot:run

# Frontend Setup (in separate terminal)
cd client
npm install
npm run dev

# Access the application
# Frontend: http://localhost:3000
# Backend API: http://localhost:8080
# H2 Console: http://localhost:8080/h2-console
```

### Docker Setup

```bash
# Build and run with Docker Compose
docker-compose up -d

# Access the application
http://localhost:3000
```

---

## 💡 Usage Examples

### API Endpoints

#### Generate Documentation
```bash
POST /api/generate/documentation
Content-Type: application/json

{
  "repositoryUrl": "https://github.com/user/project",
  "template": "standard-readme",
  "options": {
    "includeArchitecture": true,
    "includeApiDocs": true,
    "includeExamples": true
  }
}
```

#### Response
```json
{
  "id": "doc-123",
  "content": "# Project Title\n\n## Description...",
  "format": "markdown",
  "generatedAt": "2024-04-05T10:00:00Z",
  "wordCount": 1250
}
```

#### Get Generation History
```bash
GET /api/generation/history
Authorization: Bearer <token>
```

#### Create Custom Template
```bash
POST /api/templates
Content-Type: application/json

{
  "name": "custom-tech-doc",
  "description": "Technical documentation template",
  "sections": [
    "Overview",
    "Architecture",
    "API Reference",
    "Deployment Guide"
  ]
}
```

### Frontend Usage

```typescript
// Generate documentation from code
import { generateDocs } from './services/api';

const result = await generateDocs({
  repositoryUrl: 'https://github.com/user/project',
  template: 'standard-readme'
});

console.log(result.content);
```

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### Getting Started
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow existing code style and conventions
- Write comprehensive tests for new features
- Update documentation as needed
- Ensure all CI checks pass
- Use meaningful commit messages

### Areas for Contribution
- 🐛 Bug fixes and improvements
- ✨ New documentation templates
- 🎨 UI/UX enhancements
- 🧪 Test coverage
- 📚 Documentation and guides
- � Internationalization

---

