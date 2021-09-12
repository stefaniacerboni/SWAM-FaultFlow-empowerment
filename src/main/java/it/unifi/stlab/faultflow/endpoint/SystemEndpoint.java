package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.faultflow.businessLogic.controller.AddSystemController;
import it.unifi.stlab.faultflow.businessLogic.controller.RemoveSystemController;
import it.unifi.stlab.faultflow.businessLogic.controller.ShowSystemsController;
import it.unifi.stlab.faultflow.dto.inputsystemdto.InputSystemDto;
import it.unifi.stlab.faultflow.exporter.PetriNetExportMethod;
import it.unifi.stlab.faultflow.exporter.XPNExporter;
import it.unifi.stlab.faultflow.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.faultflow.mapper.FaultTreeMapper;
import it.unifi.stlab.faultflow.mapper.SystemMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.translator.PetriNetTranslator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

@Path("/system")
public class SystemEndpoint {


    @Inject
    AddSystemController addSystemController = new AddSystemController();
    @Inject
    RemoveSystemController removeSystemController = new RemoveSystemController();
    @Inject
    ShowSystemsController showSystemsController = new ShowSystemsController();


    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystem(InputSystemDto inputSystemDto) {
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        return Response.ok(FaultTreeMapper.systemToOutputSystem(sys)).build();
    }

    @POST
    @Path("/xpn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetriNetXPN(InputSystemDto inputSystemDto,
                                   @QueryParam("method") @DefaultValue("fa") String method) {
        PetriNetTranslator pnt = new PetriNetTranslator();
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        //TODO nome del file generatore casualmente + path configurabile in maniera più elegante attraverso un file di properties
        File out = new File("PetriNet.xpn");
        try {
            pnt.translate(sys, PetriNetExportMethod.fromString(method));
            XPNExporter.export(out, new OrderByComponentToXPN(sys, pnt.getPetriNet(), pnt.getMarking()));
            return Response.ok(out).header("Content-Disposition", "attachment; filename=" + "PetriNet.xpn").build();
        } catch (FileNotFoundException fnf) {
            throw new NotFoundException("File not Found");
        } catch (JAXBException e) {
            throw new BadRequestException("There's been a problem with the conversion to XPN");
        } catch (Exception e) {
            throw new InternalServerErrorException("Unexpected Server Problem");
        }
    }

    @POST
    @Path("/load")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response persistSystem(InputSystemDto inputSystemDto) {
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        addSystemController.persistSystem(sys);
        return Response.ok(FaultTreeMapper.systemToOutputSystem(sys)).build();
    }

    @GET
    @Path("/remove/{system_uuid}")
    public Response removeSystem(@PathParam("system_uuid") String systemUUID) {
        if (systemUUID == null) {
            throw new IllegalArgumentException("Please, specify System's UUID as path Parameter!");
        } else {
            try {
                removeSystemController.removeSystem(UUID.fromString(systemUUID));
            } catch (Exception e) {
                throw new Error(e.getMessage());
            }
        }
        return Response.ok().build();
    }
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystems() {
        return Response.ok(showSystemsController.getAllSystems()).build();
    }

    @GET
    @Path("/{system_uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystemInfo(@PathParam("system_uuid") String systemUUID) {
        return Response.ok(showSystemsController.getSystem(systemUUID)).build();
    }
    @PATCH
    @Path("/{system_uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSystemInfo(@PathParam("system_uuid") String systemUUID, System inputSystem) {
        return Response.ok(showSystemsController.updateSystem(systemUUID, inputSystem)).build();
    }


}
