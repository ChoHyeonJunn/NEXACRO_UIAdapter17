(function()
{
    return function()
    {
        if (!this._is_form)
            return;
        
        var obj = null;
        
        this.on_create = function()
        {
            this.set_name("Form_Work");
            this.set_titletext("Form_Work");
            if (Form == this.constructor)
            {
                this._setFormPosition(1280,720);
            }
            
            // Object(Dataset, ExcelExportObject) Initialize
            obj = new Dataset("ds_search", this);
            obj._setContents("<ColumnInfo><Column id=\"searchType\" type=\"STRING\" size=\"256\"/><Column id=\"keyword\" type=\"STRING\" size=\"256\"/></ColumnInfo><Rows><Row/></Rows>");
            this.addChild(obj.name, obj);


            obj = new Dataset("ds_list", this);
            obj._setContents("<ColumnInfo><Column id=\"ID\" type=\"STRING\" size=\"256\"/><Column id=\"NAME\" type=\"STRING\" size=\"256\"/><Column id=\"DESCRIPTION\" type=\"STRING\" size=\"256\"/><Column id=\"USE_YN\" type=\"STRING\" size=\"256\"/><Column id=\"REG_USER\" type=\"STRING\" size=\"256\"/></ColumnInfo>");
            this.addChild(obj.name, obj);
            
            // UI Components Initialize
            obj = new Combo("Combo00","28","48","112","20",null,null,null,null,null,null,this);
            obj.set_taborder("0");
            obj.set_codecolumn("codecolumn");
            obj.set_datacolumn("datacolumn");
            var Combo00_innerdataset = new nexacro.NormalDataset("Combo00_innerdataset", obj);
            Combo00_innerdataset._setContents("<ColumnInfo><Column id=\"codecolumn\" size=\"256\"/><Column id=\"datacolumn\" size=\"256\"/></ColumnInfo><Rows><Row><Col id=\"codecolumn\">ID</Col><Col id=\"datacolumn\">id</Col></Row><Row><Col id=\"codecolumn\">NAME</Col><Col id=\"datacolumn\">name</Col></Row></Rows>");
            obj.set_innerdataset(Combo00_innerdataset);
            obj.set_text("id");
            obj.set_value("ID");
            obj.set_index("0");
            this.addChild(obj.name, obj);

            obj = new Edit("Edit00","150","48","160","20",null,null,null,null,null,null,this);
            obj.set_taborder("1");
            this.addChild(obj.name, obj);

            obj = new Button("Button00","314","48","92","50",null,null,null,null,null,null,this);
            obj.set_taborder("2");
            obj.set_text("조회");
            this.addChild(obj.name, obj);

            obj = new Grid("Grid00","28","116","676","344",null,null,null,null,null,null,this);
            obj.set_taborder("3");
            obj.set_binddataset("ds_list");
            obj.set_autofittype("col");
            obj.set_autoenter("select");
            obj._setContents("<Formats><Format id=\"default\"><Columns><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/></Columns><Rows><Row size=\"28\" band=\"head\"/><Row size=\"28\"/></Rows><Band id=\"head\"><Cell text=\"ID\"/><Cell col=\"1\" text=\"NAME\" displaytype=\"editcontrol\" edittype=\"normal\"/><Cell col=\"2\" text=\"DESCRIPTION\" displaytype=\"editcontrol\" edittype=\"normal\"/><Cell col=\"3\" text=\"USE_YN\" displaytype=\"editcontrol\" edittype=\"normal\"/><Cell col=\"4\" text=\"REG_USER\" displaytype=\"editcontrol\" edittype=\"normal\"/></Band><Band id=\"body\"><Cell text=\"bind:ID\"/><Cell col=\"1\" text=\"bind:NAME\" edittype=\"normal\" displaytype=\"editcontrol\"/><Cell col=\"2\" text=\"bind:DESCRIPTION\" edittype=\"normal\" displaytype=\"editcontrol\"/><Cell col=\"3\" text=\"bind:USE_YN\" edittype=\"normal\" displaytype=\"editcontrol\"/><Cell col=\"4\" text=\"bind:REG_USER\" edittype=\"normal\" displaytype=\"editcontrol\"/></Band></Format></Formats>");
            this.addChild(obj.name, obj);

            obj = new Button("Button01","413","48","92","50",null,null,null,null,null,null,this);
            obj.set_taborder("4");
            obj.set_text("추가");
            this.addChild(obj.name, obj);

            obj = new Button("Button02","512","48","92","50",null,null,null,null,null,null,this);
            obj.set_taborder("5");
            obj.set_text("삭제");
            this.addChild(obj.name, obj);

            obj = new Button("Button03","611","48","92","50",null,null,null,null,null,null,this);
            obj.set_taborder("6");
            obj.set_text("수정");
            this.addChild(obj.name, obj);
            // Layout Functions
            //-- Default Layout : this
            obj = new Layout("default","Desktop_screen",1280,720,this,function(p){});
            this.addLayout(obj.name, obj);
            
            // BindItem Information

            
            // TriggerItem Information

        };
        
        this.loadPreloadList = function()
        {

        };
        
        // User Script
        this.registerScript("Form_Work.xfdl", function() {

        // 조회 버튼 클릭시
        this.Button00_onclick = function(obj,e)
        {
        	this.fnSearch();
        };

        //추가 버튼 클릭
        this.Button01_onclick = function(obj,e)
        {
        	var nRow = this.ds_list.addRow();
        	this.ds_list.set_rowposition(nRow);
        	this.Grid00.setCellPos(1);
        	this.Grid00.showEditor(true);
        };

        //삭제 버튼 클릭
        this.Button02_onclick = function(obj,e)
        {
        	var nRow = this.ds_list.rowposition;
        	this.ds_list.deleteRow(nRow);
        };

        // 저장 버튼 클릭
        this.Button03_onclick = function(obj,e)
        {
        	this.fnSave();
        };

        /************************************************************************
        * 데이터 조회 트랜잭션
        ************************************************************************/
        this.fnSearch = function ()
        {
        	// 조회조건 셋팅
        	this.ds_search.setColumn(0, "searchType", this.Combo00.value);
        	this.ds_search.setColumn(0, "keyword"  , this.Edit00.text);

        	var strSvcId    = "search";
        	var strSvcUrl   = "svc::selectSampleList.do";
        	var inData      = "input1=ds_search";
        	var outData     = "ds_list=output1";
        	var strArg      = "";
        	var callBackFnc = "fnCallback";
        	var isAsync     = true;

        	this.transaction(strSvcId ,     // transaction을 구분하기 위한 svc id값
        		strSvcUrl ,   	// transaction을 요청할 주소
        		inData ,     	// 입력값으로 보낼 dataset id , a=b형태로 실제이름과 입력이름을 매칭
        		outData ,     	// 처리결과값으로 받을 dataset id, a=b형태로 실제이름과 입력이름을 매칭
        		strArg ,     	// 입력값으로 보낼 arguments, a=b
        		callBackFnc ,   // transaction의 결과를 받을 Function 이름
        		isAsync);     	// 비동기통신 여부 [생략가능]
        };

        /************************************************************************
        * 데이터 저장 트랜잭션
        ************************************************************************/
        this.fnSave = function()
        {
        	var strSvcId    = "save";
        	var strSvcUrl   = "svc::updateSampleList.do";
        	var inData      = "input1=ds_list:U";
        	var outData     = "";
        	var strArg      = "";
        	var callBackFnc = "fnCallback";
        	var isAsync     = true;

        	this.transaction(strSvcId ,     // transaction을 구분하기 위한 svc id값
        		strSvcUrl ,   	// trabsaction을 요청할 주소
        		inData ,     	// 입력값으로 보낼 dataset id , a=b형태로 실제이름과 입력이름을 매칭
        		outData ,     	// 처리결과값으로 받을 dataset id, a=b형태로 실제이름과 입력이름을 매칭
        		strArg ,     	// 입력값으로 보낼 arguments, a=b
        		callBackFnc ,   // transaction의 결과를 받을 Function 이름
        		isAsync);     	// 비동기통신 여부 [생략가능]
        };

        //처리콜백 함수
        this.fnCallback = function(svcID,errorCode,errorMsg)
        {
        	// 에러 시 화면 처리 내역
        	if(errorCode != 0)
        	{
        		this.alert(errorCode+"\n"+errorMsg);
        		return;
        	}

        	switch(svcID)
        	{
        		case "search":
        			if(this.ds_list.rowcount < 1){
        				this.alert("조회된 결과가 없습니다.");
        			}
        			break;

        		case "save":
        			this.alert("저장 되었습니다");
        			this.fnSearch();

        			break;
        	}

        };



        });
        
        // Regist UI Components Event
        this.on_initEvent = function()
        {
            this.Button00.addEventHandler("onclick",this.Button00_onclick,this);
            this.Button01.addEventHandler("onclick",this.Button01_onclick,this);
            this.Button02.addEventHandler("onclick",this.Button02_onclick,this);
            this.Button03.addEventHandler("onclick",this.Button03_onclick,this);
        };
        this.loadIncludeScript("Form_Work.xfdl");
        this.loadPreloadList();
        
        // Remove Reference
        obj = null;
    };
}
)();
