package hbi.core.hand.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import hbi.core.hand.dto.*;
import hbi.core.hand.mapper.*;
import hbi.core.hand.service.IOmOrderHeadersService;
import hbi.core.hand.utils.PDFReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderHeadersServiceImpl extends BaseServiceImpl<OmOrderHeaders> implements IOmOrderHeadersService{

    @Autowired
    OmOrderHeadersMapper omOrderHeadersMapper;
    @Autowired
    OmOrderLinesMapper omOrderLinesMapper;

    @Override
    public boolean omHeaderDelete(List<OmOrderHeaders> omOrderHeaderss) {
        Iterator iterator = omOrderHeaderss.iterator();
        OmOrderHeaders omOrderHeaders;
        OmOrderLines omOrderLines = new OmOrderLines();
        while (iterator.hasNext()) {
            omOrderHeaders = (OmOrderHeaders) iterator.next();
            omOrderHeadersMapper.deleteByPrimaryKey(omOrderHeaders);
            omOrderLines.setHeaderId(omOrderHeaders.getHeaderId());
            omOrderLinesMapper.deleteLinesByHeaderId(omOrderLines);

        }
        return true;
    }

    @Override
    public List<OmOrderHeaders> selectInfo(OmOrderHeaders log) {
        return null;
    }

    @Override
    public List<OmOrderHeaders> findAll() {
        return omOrderHeadersMapper.findAll();


    }

    @Autowired
    OrgCompanysMapper orgCompanysMapper;
    @Autowired
    ArCustomersMapper arCustomersMapper;
    @Autowired
    InvInventoryItemsMapper invInventoryItemsMapper;
    @Override
    public List<OmOrderHeaders> printPDF(IRequest requestCtx, @StdWho List<OmOrderHeaders> States) {

        //String  filePath = request.getSession().getServletContext().getRealPath("/")+"/TradeOrder.xls";
        //File file=new File(filePath);
        PDFReport pdfPrint = new PDFReport();

        OmOrderHeaders   notice=null;
        //List<OmOrderHeaders> trade=new ArrayList<OmOrderHeaders>();

        for (OmOrderHeaders  state : States) {
            if (state.getHeaderId() != null) {
                notice=state;
            }
        }

        File file = new File("C:\\Users\\PB\\Desktop\\"+notice.getOrderNumber()+".pdf");
        List<OmOrderLines> omOrderLines = omOrderLinesMapper.selectSumPrice(notice);


        try {

           BaseFont bfChinese=PDFReport.bfChinese;
           Font headfont =PDFReport.headfont;
           Font keyfont=PDFReport.keyfont;
           Font textfont=PDFReport.textfont;

            file.createNewFile();
            Document document = new Document();

            document.setPageSize(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table= pdfPrint.createTable(6);

            table.addCell(pdfPrint.createCell(".订单打印：", headfont, Element.ALIGN_LEFT,6,false));
            table.addCell(pdfPrint.createCell(" ", keyfont, Element.ALIGN_LEFT,6,false));
            table.addCell(pdfPrint.createCell(" ", keyfont, Element.ALIGN_LEFT,6,false));


            table.addCell(pdfPrint.createCell("订单编号：", keyfont, Element.ALIGN_RIGHT,0,false));
            //table.addCell(pdfPrint.createCell(notice.getOrderNumber(), textfont, Element.ALIGN_CENTER));
            table.addCell(pdfPrint.createCell(notice.getOrderNumber(), textfont,Element.ALIGN_LEFT));
            table.addCell(pdfPrint.createCell("公司名称：", keyfont, Element.ALIGN_RIGHT,0,false));
            OrgCompanys orgCompanys = orgCompanysMapper.selectByPrimaryKey(notice.getCompanyId());
            table.addCell(pdfPrint.createCell(orgCompanys.getCompanyName(), textfont, Element.ALIGN_LEFT));
            ArCustomers arCustomers = arCustomersMapper.selectByPrimaryKey(notice.getCustomerId());
            table.addCell(pdfPrint.createCell("客户名称：", keyfont, Element.ALIGN_RIGHT,0,false));
            table.addCell(pdfPrint.createCell(arCustomers.getCustomerName(), textfont, Element.ALIGN_LEFT));
            table.addCell(pdfPrint.createCell(" ", keyfont, Element.ALIGN_LEFT,6,false));


            table.addCell(pdfPrint.createCell("订单日期：", keyfont, Element.ALIGN_RIGHT,0,false));
            table.addCell(pdfPrint.createCell(new SimpleDateFormat("yyyy/MM/dd").format(notice.getOrderDate()), textfont, Element.ALIGN_LEFT));
            long sum = 0;
            for(int i=0;i<omOrderLines.size();i++) {
                OmOrderLines ot = new OmOrderLines();
                ot = omOrderLines.get(i);
                sum = sum + ot.getOrderdQuantity()*ot.getUnitSellingPrice();

            }


            table.addCell(pdfPrint.createCell("订单总金额：", keyfont, Element.ALIGN_RIGHT,0,false));
            table.addCell(pdfPrint.createCell(String.valueOf(sum), textfont, Element.ALIGN_LEFT));
            table.addCell(pdfPrint.createCell("订单状态：", keyfont, Element.ALIGN_RIGHT,0,false));
            String s="";
            if(notice.getOrderStatus().equals("NEW")){
                s="新建";
            }else if(notice.getOrderStatus().equals("SUBMITED")){
                s="已提交";
            }else if(notice.getOrderStatus().equals("APPROVED")){
                s="已审批";
            }else if(notice.getOrderStatus().equals("REJECTED")){
                s="已拒绝";
            }
            table.addCell(pdfPrint.createCell(s, textfont, Element.ALIGN_LEFT));
            table.addCell(pdfPrint.createCell(" ", keyfont, Element.ALIGN_LEFT,6,false));
            table.addCell(pdfPrint.createCell(" ", keyfont, Element.ALIGN_LEFT,6,false));

        table.addCell(pdfPrint.createCell("主要：", headfont, Element.ALIGN_LEFT,6,false));
		table.addCell(pdfPrint.createCell("物料编码", textfont, Element.ALIGN_CENTER));
		table.addCell(pdfPrint.createCell("物料描述", textfont, Element.ALIGN_CENTER));
		table.addCell(pdfPrint.createCell("产品单位", textfont, Element.ALIGN_CENTER));
		table.addCell(pdfPrint.createCell("数量", textfont, Element.ALIGN_CENTER));
        table.addCell(pdfPrint.createCell("销售单价", textfont, Element.ALIGN_CENTER));
        table.addCell(pdfPrint.createCell("金额", textfont, Element.ALIGN_CENTER));

		for(int i=0;i<omOrderLines.size();i++){
            OmOrderLines ot = new OmOrderLines ();
            ot = omOrderLines.get(i);
            InvInventoryItems invInventoryItems = invInventoryItemsMapper.selectByPrimaryKey(ot.getInventoryItemId());
			table.addCell(pdfPrint.createCell(invInventoryItems.getItemCode(), textfont));
			table.addCell(pdfPrint.createCell(invInventoryItems.getItemDescription(), textfont));
			table.addCell(pdfPrint.createCell(ot.getOrderQuantityUom(), textfont));
			table.addCell(pdfPrint.createCell(String.valueOf(ot.getOrderdQuantity()), textfont));
            table.addCell(pdfPrint.createCell(String.valueOf(ot.getUnitSellingPrice()), textfont));
            table.addCell(pdfPrint.createCell(String.valueOf(ot.getOrderdQuantity()*ot.getUnitSellingPrice()), textfont));
		}
		try {
			document.add(table);

		}catch(Exception e){e.printStackTrace(); }

		document.close();
        }catch(Exception e){e.printStackTrace();}


        return States;
    }




}