@Controller
public class JavaServerExample {
    @RequestMapping(value = "<callback>", method = RequestMethod.GET)
    public void gatewayCallback(HttpServletRequest request, HttpServletResponse response) {
        if(hasError(request)) {
            //process gateway error
        }else {
            String paramUserInfo = request.getParameter("userInfo");
            if(paramUserInfo != null) {
                //validate MD5(paramUserInfo) equals request.getParameter("md5signature")
                byte[] bytesData = Base64.getDecoder().decode(paramUserInfo);
                try {
                    String userInfo = new String(bytesData, "utf-8");
                    //use json userInfo
                    //send redirect to your home page
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private boolean hasError(HttpServletRequest request) {
        return request.getParameter("error") != null;
    }
}