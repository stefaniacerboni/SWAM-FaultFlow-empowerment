# FaultFlow Simulation System

## Overview

FaultFlow is a comprehensive tool for modeling and simulating fault propagation within systems. It integrates with a Model-Driven Engineering (MDE) approach, leveraging structural artifacts such as SysML Block Definition Diagrams (BDD) and dependability artifacts like stochastic Fault Trees (FT). The primary focus of FaultFlow is to enable the simulation of fault propagation using Stochastic-Time Petri Net (STPN) models, analyze system reliability, and support rejuvenation and transient fault handling.

## Features

- **System Modeling**: Define systems and their failure logic in JSON format.
- **Import Functionality**: Load system models via the webapp through a user-friendly interface.
- **Online Editor**: Edit system models directly within the webapp.
- **STPN Transformation**: Convert system models to STPN models for further analysis.
- **Simulation**: Run simulations to observe fault propagation and system behavior over time.
- **Rejuvenation**: Implement rejuvenation intervals for system components.
- **Transient Faults**: Handle transient faults with specific time windows.
- **Fault Injection**: Introduce faults at specific times and observe their impact.
- **Results Analysis**: Export results for analysis using external tools like Oris Tool.

## Installation

### Prerequisites

- Java 11+
- Node.js and npm
- Apache Maven
- Wildfly Application Server

### Backend

1. Clone the repository:
```bash
   git clone https://github.com/your-repo/faultflow.git
   cd faultflow
```

2. Build the project:
```bash
   mvn clean install
```
3. Deploy the backend server:
   - Copy the generated WAR file from the `target` directory to the Wildfly deployments directory:
```bash
     cp target/faultflow.war $WILDFLY_HOME/standalone/deployments/
```
4. Start the Wildfly server:
   - Navigate to the Wildfly bin directory and start the server:
```bash
     cd $WILDFLY_HOME/bin
     ./standalone.sh
```
### Frontend

1. Navigate to the frontend directory:
```bash
   cd faultflow-frontend
```
2. Install dependencies:
```bash
   npm install
```
3. Build the frontend:
```bash
   ng build --prod
```
4. Deploy the frontend:
```bash
   cp -r dist/faultflow-frontend/* ../web/
```
## Usage

### Login

1. Navigate to the login page of the webapp.
2. Enter your username and password to log in.

### Importing Systems

1. Click on the "Import" tab.
2. Paste your JSON model in the text area provided.
3. Click the "Import" button to load the model into the webapp.

### Viewing Systems

1. Navigate to the "Systems" tab.
2. View the list of systems loaded into the webapp.
3. Click on a system to view its details, download as Petri Net, view as JSON, or see related images.

### Running Simulations

1. Use the "Simulate" button to initiate a simulation.
2. Specify the fault injections and parameters.
3. Observe the simulation results and analyze the system behavior.

## Documentation

### FaultFlow Guide

**FaultFlow enables a MDE Approach in a tool-chain perspective, exploiting information provided by structural - i.e., SysML Block Definition Diagrams (BDD) - and dependability - i.e., stochastic Fault Trees (FT) - artifacts.**

**The basic idea behind the tool can be summarized in the following three macro stages:**

**1st STAGE: Design specification**

- A system with its failure logic can be specified in JSON format, thus configuring the underlying reference meta-model.
- The system can be loaded into the webapp through the "Import" tab, specifying the JSON in the correct format.
- The online editor for each system already loaded can be opened by clicking on the "View as JSON" button.

**2nd STAGE: Failure Logic-2-STPN Transformation**

- Each system can be translated into an output Stochastic-Time Petri Net (STPN) model by clicking the "Download as Petri Net" button of each system.
- After each export operation, an STPN (as an XPN file) is generated by the FaultFlow core.

**3rd STAGE: STPN Analysis**

- Through an external tool (e.g., Oris Tool), each XPN file can be opened and analyzed. Note that the Oris Tool requires the installation of Java 11+ (refer to the Oris guide for the installation).

## Contact

For any questions or issues, please contact the project maintainer at [stefania.cerboni@edu.unifi.it].

---

Thank you for using FaultFlow! We hope it helps you in your system modeling and simulation efforts.