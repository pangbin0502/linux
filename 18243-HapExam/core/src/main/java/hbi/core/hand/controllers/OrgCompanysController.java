package hbi.core.hand.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hbi.core.hand.dto.OrgCompanys;
import hbi.core.hand.service.IOrgCompanysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

    @Controller
    public class OrgCompanysController extends BaseController{

    @Autowired
    private IOrgCompanysService service;


    @RequestMapping(value = "/hap/org/companys/query")
    @ResponseBody
    public ResponseData query(OrgCompanys dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        return new ResponseData(service.getAll());
    }

        @RequestMapping(value = "/hap/org/companys/getName")
        @ResponseBody
        public ResponseData getName(OrgCompanys dto,@RequestParam Long companyId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
            return new ResponseData(service.getName(companyId));
        }

    @RequestMapping(value = "/hap/org/companys/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OrgCompanys> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hap/org/companys/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<OrgCompanys> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }