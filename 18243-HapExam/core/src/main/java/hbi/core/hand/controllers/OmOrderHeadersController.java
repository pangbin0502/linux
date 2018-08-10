package hbi.core.hand.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hbi.core.hand.dto.*;
import hbi.core.hand.mapper.*;
import hbi.core.hand.service.IOmOrderHeadersService;
import hbi.core.hand.service.IOmOrderLinesService;
import hbi.core.hand.utils.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
    public class OmOrderHeadersController extends BaseController{

    @Autowired
    private IOmOrderHeadersService service;
    @Autowired
    private OmOrderLinesMapper omOrderLinesMapper;
    @Autowired
    private OmOrderHeadersMapper omOrderHeadersMapper;
//        @Autowired
//        private IExportService excelService;
//        @Autowired
//        private ObjectMapper objectMapper;

    private List<OmOrderHeaders> omOrderHeaderss;

    public List<OmOrderHeaders> getOmOrderHeaderss() {
        return omOrderHeaderss;
    }

    public void setOmOrderHeaderss(List<OmOrderHeaders> omOrderHeaderss) {
        this.omOrderHeaderss = omOrderHeaderss;
    }


    private List<OmOrderLines> list1;

    public List<OmOrderLines> getList1() {
        return list1;
    }

    public void setList1(List<OmOrderLines> list1) {
        this.list1 = list1;
    }

    @RequestMapping(value = "/hap/om/order/headers/query")
    @ResponseBody
    public ResponseData query(OmOrderHeaders dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        List<OmOrderHeaders> biglist=new ArrayList<OmOrderHeaders>();
        if(getList1()!=null) {

            for (int i = 0; i < getList1().size(); i++) {
                dto.setHeaderId(getList1().get(i).getHeaderId());
                IRequest requestContext = createRequestContext(request);
                if(service.select(requestContext, dto, page, pageSize)!=null)
                if(service.select(requestContext, dto, page, pageSize).size()>0)
                    biglist.add(service.select(requestContext, dto, page, pageSize).get(0));

            }
            setList1(null);
            setOmOrderHeaderss(biglist);
            return new ResponseData(biglist);
        }
            IRequest requestContext = createRequestContext(request);
            List<OmOrderHeaders> list = service.select(requestContext, dto, page, pageSize);
            setOmOrderHeaderss(list);
            return new ResponseData(list);

    }

    @Autowired
    private IOmOrderLinesService omOrderLinesService;

    @RequestMapping(value = "/hap/om/order/headers/selectLine")
    @ResponseBody
    public ResponseData query(Long inventoryItemId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        OmOrderLines omOrderLines = new OmOrderLines();
        omOrderLines.setInventoryItemId(inventoryItemId);
        List<OmOrderLines> list = omOrderLinesService.select(requestContext, omOrderLines, page, pageSize);
        setList1(list);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/hap/om/order/headers/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OmOrderHeaders> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hap/om/order/headers/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<OmOrderHeaders> dto){
        //service.batchDelete(dto);
        service.omHeaderDelete(dto);
        return new ResponseData();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        @RequestMapping(value = "/hap/om/order/headers/input" ,method= RequestMethod.POST)
        @ResponseBody
        public ResponseData saveCuxLines(@RequestBody List<OmOrderHeaders> omOrderHeaderss ,BindingResult result, HttpServletRequest request) {
            IRequest requestCtx = createRequestContext(request);
            for (OmOrderHeaders omOrderHeaders:omOrderHeaderss){


                if(omOrderHeaders.getHeaderId()==null){
                    service.insertSelective(requestCtx,omOrderHeaders);
                    if (omOrderHeaders.getOmOrderLinesList()!=null){
                        processLookupValue(omOrderHeaders);
                    }

                }else if(omOrderHeaders.getHeaderId()!=null){
                    service.updateByPrimaryKeySelective(requestCtx,omOrderHeaders);
                    if (omOrderHeaders.getOmOrderLinesList()!=null){
                        processLookupValue(omOrderHeaders);
                    }

                }
            }
            return new ResponseData(omOrderHeaderss);

        }



        @Transactional(propagation = Propagation.SUPPORTS)
        private void processLookupValue(OmOrderHeaders omOrderHeaders){
            for (OmOrderLines omOrderLines : omOrderHeaders.getOmOrderLinesList()){
                if (omOrderLines.getHeaderId()==null){
                    omOrderLines.setHeaderId(omOrderHeaders.getHeaderId());
                    omOrderLines.setCompanyId(omOrderHeaders.getCompanyId());
                    omOrderLinesMapper.insertSelective(omOrderLines);
                }else if(omOrderLines.getHeaderId()!=null){
                    omOrderLinesMapper.updateByPrimaryKeySelective(omOrderLines);
                }
            }
        }

        @Autowired
         OrgCompanysMapper orgCompanysMapper;
        @Autowired
        ArCustomersMapper arCustomersMapper;
        @Autowired
        InvInventoryItemsMapper invInventoryItemsMapper;
        /*             excel导出功能实现                  */
        @RequestMapping(value = "/hap/om/order/headers/exportXls")
        public void createXLS(HttpServletRequest request,HttpServletResponse response,OmOrderHeaders dto) throws IOException {

            List<OmOrderHeaders> list = getOmOrderHeaderss();

           // List<OmOrderLines>  lineslist = omOrderLinesMapper.findAll();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("订单信息");
            //创建标题行
            HSSFRow headRow = sheet.createRow(0);
            headRow.createCell(0).setCellValue("销售订单号");
            headRow.createCell(1).setCellValue("公司名称");
            headRow.createCell(2).setCellValue("客户名称");
            headRow.createCell(3).setCellValue("订单日期");
            headRow.createCell(4).setCellValue("订单状态");
            headRow.createCell(5).setCellValue("物料编码");
            headRow.createCell(6).setCellValue("物料描述");
            headRow.createCell(7).setCellValue("数量");
            headRow.createCell(8).setCellValue("销售单价");
            headRow.createCell(9).setCellValue("金额");

            for(OmOrderHeaders omOrderHeaders:list){
                int i = sheet.getLastRowNum() + 1;
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                dataRow.createCell(0).setCellValue(omOrderHeaders.getOrderNumber());
                OrgCompanys orgCompanys = orgCompanysMapper.selectByPrimaryKey(omOrderHeaders.getCompanyId());
                dataRow.createCell(1).setCellValue(orgCompanys.getCompanyName());
                ArCustomers arCustomers = arCustomersMapper.selectByPrimaryKey(omOrderHeaders.getCustomerId());
                dataRow.createCell(2).setCellValue(arCustomers.getCustomerName());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String format1 = format.format(omOrderHeaders.getOrderDate());
                dataRow.createCell(3).setCellValue(format1);
                String s="";
                if(omOrderHeaders.getOrderStatus().equals("NEW")){
                    s="新建";
                }else if(omOrderHeaders.getOrderStatus().equals("SUBMITED")){
                    s="已提交";
                }else if(omOrderHeaders.getOrderStatus().equals("APPROVED")){
                    s="已审批";
                }else if(omOrderHeaders.getOrderStatus().equals("REJECTED")){
                    s="已拒绝";
                }
                dataRow.createCell(4).setCellValue(s);
                List<OmOrderLines>  lineslist = omOrderLinesMapper.findAll(omOrderHeaders.getHeaderId());
                int lastRowNum = i;
                for (OmOrderLines omOrderLines:lineslist) {
                    if(sheet.getLastRowNum()==lastRowNum) {
                        InvInventoryItems invInventoryItems = invInventoryItemsMapper.selectByPrimaryKey(omOrderLines.getInventoryItemId());
                        dataRow.createCell(5).setCellValue(invInventoryItems.getItemCode());
                        dataRow.createCell(6).setCellValue(invInventoryItems.getItemDescription());
                        dataRow.createCell(7).setCellValue(omOrderLines.getOrderdQuantity());
                        dataRow.createCell(8).setCellValue(omOrderLines.getUnitSellingPrice());
                        long sum = omOrderLines.getOrderdQuantity()*omOrderLines.getUnitSellingPrice();
                        dataRow.createCell(9).setCellValue(sum);
                        lastRowNum=-1;
                    }else{
                        dataRow = sheet.createRow( sheet.getLastRowNum()+1);
                        InvInventoryItems invInventoryItems = invInventoryItemsMapper.selectByPrimaryKey(omOrderLines.getInventoryItemId());
                        dataRow.createCell(5).setCellValue(invInventoryItems.getItemCode());
                        dataRow.createCell(6).setCellValue(invInventoryItems.getItemDescription());
                        dataRow.createCell(7).setCellValue(omOrderLines.getOrderdQuantity());
                        dataRow.createCell(8).setCellValue(omOrderLines.getUnitSellingPrice());
                        long sum = omOrderLines.getOrderdQuantity()*omOrderLines.getUnitSellingPrice();
                        dataRow.createCell(9).setCellValue(sum);
                    }
                }
            }
            //第三步：使用输出流进行文件下载（一个流、两个头）

            String filename = "订单信息.xls";
            String mimeType = request.getServletContext().getMimeType(filename);
            ServletOutputStream out = response.getOutputStream();
            String agent =request.getHeader("User-Agent");
            filename = FileUtils.encodeDownloadFilename(filename, agent);
            response.setHeader("Content-Disposition", "attachment;filename="+filename);
            workbook.write(out);
        }



        @RequestMapping(value = "/hap/om/order/headers/pdfPrint")
        @ResponseBody
        public ResponseData printPdf(@RequestBody List<OmOrderHeaders> dto,
                                     BindingResult result, HttpServletRequest request,HttpServletResponse response)  {
            IRequest requestCtx = createRequestContext(request);
            return new ResponseData(service.printPDF(requestCtx, dto));
        }



    }