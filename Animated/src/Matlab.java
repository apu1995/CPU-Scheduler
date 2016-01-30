import matlabcontrol.*;
public class Matlab {
	
	MatlabProxyFactory factory = new MatlabProxyFactory();
	MatlabProxy proxy = factory.getProxy();
	
	
	public Matlab() throws MatlabConnectionException, MatlabInvocationException
    {
		proxy.eval("fileID = fopen('PieChartPass.txt','r');");
		proxy.eval("formatSpec = '%d';");
		proxy.eval("sizeA = [1 Inf];");
		proxy.eval("A = fscanf(fileID,formatSpec,sizeA)");
		proxy.eval("fclose(fileID);");
		proxy.eval("figure;");
		proxy.eval("pie3(A)");
		proxy.eval("saveas(gcf,'pie.png');");
    }
   
    public void stop() throws MatlabConnectionException, MatlabInvocationException
    {
        proxy.disconnect();
    }

}