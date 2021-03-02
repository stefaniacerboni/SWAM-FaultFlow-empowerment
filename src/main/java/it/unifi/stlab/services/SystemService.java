package it.unifi.stlab.services;

import it.unifi.stlab.dto.inputsystemdto.InputSystemDto;
import it.unifi.stlab.dto.inputsystemdto.faulttree.InputFaultTreeDto;
import it.unifi.stlab.exporter.PetriNetExportMethod;
import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportToXPN;
import it.unifi.stlab.model.knowledge.composition.System;
import it.unifi.stlab.translator.PetriNetTranslator;
import it.unifi.stlab.utils.builder.SimpleSystem02Builder;
import it.unifi.stlab.services.mapper.FaultTreeMapper;
import it.unifi.stlab.services.mapper.SystemMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

@Path("/system")
public class SystemService {
    @POST
    @Path("/xpn/{method}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetriNetXPN(InputSystemDto inputSystemDto, @PathParam("method") String method) {
        PetriNetTranslator pnt = new PetriNetTranslator();
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        File out = new File("prova.xpn");
        try {
            pnt.translate(sys, PetriNetExportMethod.fromString(method));
            XPNExporter.export(out, new OrderByComponentToXPN(sys, pnt.getPetriNet(), pnt.getMarking()));
            return Response.ok(out).header("Content-Disposition", "attachment; filename=" + "PetriNet.xpn").build();
        }
        catch (FileNotFoundException fnf ){
            throw new NotFoundException("File not Found");
        }
        catch(JAXBException e){
            throw new BadRequestException("There's been a problem with the conversion to XPN");
        }
        catch (Exception e){
            throw new InternalServerErrorException("Unexpected Server Problem");
        }    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystem(InputSystemDto inputSystemDto){
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        return Response.ok(FaultTreeMapper.systemToOutputSystem(sys)).build();
    }

}
