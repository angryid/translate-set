package com.lsj.trans;

import com.lsj.http.HttpParams;
import com.lsj.http.HttpPostParams;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaiduDispatch extends AbstractDispatch {
	
	static{
		BaiduDispatch dispatch = new BaiduDispatch();
		classMap.put("baidu", dispatch);
		classMap.put("Baidu", dispatch);
	}
	
	private BaiduDispatch(){
		langMap.put(LANG.EN, "en");
		langMap.put(LANG.ZH, "zh");
	}
	
	@Override
	public String getResponse(LANG from, LANG targ, String query) throws Exception{
		
		HttpParams params = new HttpPostParams()
				.put("from", langMap.get(from))
				.put("to", langMap.get(targ))
				.put("query", query)
				.put("transtype", "translang")
				.put("simple_means_flag", "3");
		
		return params.send("http://fanyi.baidu.com/v2transapi");
	}
	
	@Override
	protected String parseString(String jsonString){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray segments = jsonObject.getJSONObject("trans_result").getJSONArray("data");
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<segments.size(); i++){
			result.append(i==0?"":"\n");
			result.append(segments.getJSONObject(i).getString("dst"));
		}
		return new String(result);
	}
}
