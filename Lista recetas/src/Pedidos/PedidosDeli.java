package Pedidos;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;


public class PedidosDeli {
	
	static boolean estienda = false;
	static String TiendaElegida = "1";
	static String agregadorEle = "PEDIDOSYA";


	public static void esTienda (boolean tienda)
	{
		estienda = tienda;
	}
	
	public static void tiendaElegida (String tienda)
	{
		TiendaElegida = tienda;
	}
	
	
	public static void agregadorElegido (String agregador)
	{
		agregadorEle = agregador;
	}
	
	public static Object[][] pedidosTablaPEYA () throws Exception {
		Conexion c = new Conexion();
		Connection conn = c.conexionDeli();
		String query = "";
		
		if (!estienda) {
		 query = "select top 30 proveedor, numpedido, fechaingreso, fechasolicitada, fechapos, tienda, cliente, montopedido  from pedidos where marca = 'BK' and proveedor = '"+agregadorEle+"' order by fechaingreso desc";
		}
		else 
		{
			 query = "select top 30 proveedor, numpedido, fechaingreso, fechasolicitada, fechapos, tienda, cliente, montopedido  from pedidos where tienda = "+TiendaElegida+" and marca = 'BK' and proveedor = '"+agregadorEle+"' order by fechaingreso desc";
				}
				
		PreparedStatement pst = conn.prepareStatement(query);
		System.out.println(query);
		ResultSet rs = pst.executeQuery();
		
		Object[][] data = new Object [31][8];
		
		data [0][0] = "PROVEEDOR";  
		data [0][1] = "NUMERO PEDIDO"; 
		data [0][2] = "FECHA INGRESO"; 
		data [0][3] = "FECHA SOLICITADA"; 
		data [0][4] = "FECHA POS"; 
		data [0][5] = "TIENDA"; 
		data [0][6] = "CLIENTE"; 
		data [0][7] = "MONTO"; 
		 
		
		
		int count = 1;
				while (rs.next())
				{	
					String proveedor = rs.getString("proveedor");
					String numpedido = rs.getString("numpedido");
					String fechaingreso = rs.getString("fechaingreso");
					String fechasolicitada = rs.getString("fechasolicitada");
					String fechapos = rs.getString("fechapos");
					String tienda = rs.getString("tienda");
					String cliente = rs.getString("cliente");
					String montopedido = rs.getString("montopedido");
					
					data [count][0] = proveedor;
					data [count][1] = numpedido;
					data [count][2] = fechaingreso;
					data [count][3] = fechasolicitada;
					data [count][4] = fechapos;
					data [count][5] = tienda;
					data [count][6] = cliente;
					data [count][7] = montopedido;
							
					
					
					count ++;
				}
				
				System.out.println(data[0][6]);
				
				
				return data;	
		}


}
