package Pedidos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		int numeroDeRegistros = 0;
		Conexion c = new Conexion();
		Connection conn = c.conexionDeli();
		String query = "select codigoPoS from rec_producto";
		
		String Resultado = "";
		
		
		PreparedStatement pst = conn.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		ArrayList<String> listaProductos = new ArrayList<String>();
		
		while (rs.next())
		{
			if (rs.getString("CodigoPOS") != null)
			listaProductos.add(rs.getString("CodigoPOS"));
			
		}
		
		for (int i = 0; i < listaProductos.size(); i++)
		{
			//System.out.println(listaProductos.get(i));
			
			String query2 = "select nombre as Nombre_producto \r\n"
					+ "from  rec_producto \r\n"
					+ "where codigoPOS =  '"+listaProductos.get(i)+"' and  marca_id = '27'";
			
			String query3 = "select 'I' as Es_Subreceta, ier.nivel_id as NivelID, i.codigoPOS as CodigoPOS_Insumo, i.nombre as Nombre_Insumo, ici.cantidad as Cantidad_ingrediente, p.CodigoPOS as CodigoPOS_Producto\r\n"
					+ "from rec_insumos i \r\n"
					+ "inner join REC_INGREDIENTE_CON_INSUMO ici on ici.insumo_id = i.id\r\n"
					+ "inner join REC_VERSION_DE_RECETA vr on vr.ingredientes_id = ici.INGREDIENTES_DE_RECETA_ID\r\n"
					+ "inner join rec_recetas r on vr.id = r.versionActual_id\r\n"
					+ "inner join  REC_IDENTIFICADOR_EXTERNO_DE_RECETA  ier on r.identificadorExterno_id = ier.id\r\n"
					+ "inner join rec_producto p on ier.producto_id = p.id\r\n"
					+ "where p.codigoPOS =  '"+listaProductos.get(i)+"' and  p.marca_id = '27'\r\n";
					
			      String query4 =  "select  'SR' as Es_Subreceta, p2.CodigoPOS as CodigoPOS_Producto, p2.nombre as Nombre_Insumo, '0' as Cantidad_ingrediente, p2.CodigoPOS as CodigoPOS_Producto\r\n"
					+ "from REC_VERSION_DE_RECETA vr2 \r\n"
					+ "inner join rec_recetas r2 on vr2.id = r2.versionActual_id\r\n"
					+ "inner join  REC_IDENTIFICADOR_EXTERNO_DE_RECETA  ier2 on r2.identificadorExterno_id = ier2.id\r\n"
					+ "inner join rec_producto p2 on ier2.producto_id = p2.id\r\n"
					+ "where r2.id in (select icr.receta_id\r\n"
					+ "from REC_INGREDIENTE_CON_RECETA ICR\r\n"
					+ "inner join REC_VERSION_DE_RECETA vr on vr.ingredientes_id = ICR.INGREDIENTES_DE_RECETA_ID\r\n"
					+ "inner join rec_recetas r on vr.id = r.versionActual_id\r\n"
					+ "inner join  REC_IDENTIFICADOR_EXTERNO_DE_RECETA  ier on r.identificadorExterno_id = ier.id\r\n"
					+ "inner join rec_producto p on ier.producto_id = p.id\r\n"
					+ "where p.codigoPOS =  '"+listaProductos.get(i)+"' and  p.marca_id = '27')";
			      
			      
			      int indexDeSubeceta= 1;
			      String query5 = "SELECT * FROM (\r\n"
			      		+ "  SELECT\r\n"
			      		+ "    ROW_NUMBER() OVER (ORDER BY icr.id) AS rownumber, icr.cantidad as cantidad \r\n"
			      		+ "     from REC_INGREDIENTE_CON_RECETA ICR\r\n"
			      		+ "inner join REC_VERSION_DE_RECETA vr on vr.ingredientes_id = ICR.INGREDIENTES_DE_RECETA_ID\r\n"
			      		+ "inner join rec_recetas r on vr.id = r.versionActual_id\r\n"
			      		+ "inner join  REC_IDENTIFICADOR_EXTERNO_DE_RECETA  ier on r.identificadorExterno_id = ier.id\r\n"
			      		+ "inner join rec_producto p on ier.producto_id = p.id\r\n"
			      		+ "where p.codigoPOS =  '"+listaProductos.get(i)+"' and  p.marca_id = '27' \r\n"
			      		+ ") AS foo\r\n"
			      		+ "WHERE rownumber = "+indexDeSubeceta+"";
			      
			      
			      String query6 ="select n.nombre as Nivel\r\n"
			      		+ "from  rec_producto p\r\n"
			      		+ "inner join REC_IDENTIFICADOR_EXTERNO_DE_RECETA i on i.producto_id = p.id\r\n"
			      		+ "inner join REC_NIVELES n on i.nivel_id = n.id\r\n"
			      		+ "where p.codigoPOS = '"+listaProductos.get(i)+"'and  p.marca_id = '27' \r\n";
			
			
			String Producto = "";
			String Nivel =  ""; 
			PreparedStatement pst2 = conn.prepareStatement(query2);
			ResultSet rs2 = pst2.executeQuery();
			while (rs2.next())
			{
				Producto = rs2.getString("Nombre_producto");
				//System.out.println(Producto);
			}
			
			PreparedStatement pst6 = conn.prepareStatement(query6);
			ResultSet rs6 = pst6.executeQuery();
			while (rs6.next())
			{
				Nivel = rs6.getString("Nivel");
				//System.out.println(Producto);
			}
			
			
			PreparedStatement psts = conn.prepareStatement(query3);
			ResultSet rss = psts.executeQuery();
			while (rss.next())
			{	
				Resultado += ""+listaProductos.get(i)+";";
				Resultado += Producto+";";				
				Resultado += (Nivel+";");
				//Resultado += (rss.getString("CodigoPOS_Insumo")+";");
				Resultado += (rss.getString("Nombre_Insumo")+";");
				Resultado += (rss.getString("Cantidad_ingrediente")+";");
				Resultado += (rss.getString("Es_Subreceta")+";");
				Resultado += (rss.getString("CodigoPOS_Insumo")+";");
				Resultado += "\r\n";	
				
				numeroDeRegistros ++;
				System.out.println(Resultado);
				
			}
			PreparedStatement pst4 = conn.prepareStatement(query4);
			ResultSet rss4 = pst4.executeQuery();
			while (rss4.next())
			{	
				Resultado += ""+listaProductos.get(i)+";";
				Resultado += Producto+";";
				Resultado += (Nivel+";");
				//Resultado += (rss.getString("CodigoPOS_Insumo")+";");
				Resultado += (rss4.getString("Nombre_Insumo")+";");
				PreparedStatement pst5 = conn.prepareStatement(query5);
				ResultSet rss5 = pst5.executeQuery();
				while (rss5.next())
				{	
					Resultado += (rss5.getString("cantidad")+";");
					indexDeSubeceta++;
					query5 = "SELECT * FROM (\r\n"
							+ "SELECT\r\n"
							+ "ROW_NUMBER() OVER (ORDER BY icr.id) AS rownumber, icr.cantidad as cantidad \r\n"
							+ "from REC_INGREDIENTE_CON_RECETA ICR\r\n"
							+ "inner join REC_VERSION_DE_RECETA vr on vr.ingredientes_id = ICR.INGREDIENTES_DE_RECETA_ID\r\n"
							+ "inner join rec_recetas r on vr.id = r.versionActual_id\r\n"
							+ "inner join  REC_IDENTIFICADOR_EXTERNO_DE_RECETA  ier on r.identificadorExterno_id = ier.id\r\n"
							+ "inner join rec_producto p on ier.producto_id = p.id\r\n"
							+ "where p.codigoPOS =  '"+listaProductos.get(i)+"' and  p.marca_id = '27' \r\n"
							+ ") AS foo\r\n"
							+ "WHERE rownumber = "+indexDeSubeceta+"";
					
				}
				Resultado += (rss4.getString("Es_Subreceta")+";");
				Resultado += (rss4.getString("CodigoPOS_Producto")+";");
				Resultado += "\r\n";	
				numeroDeRegistros ++;
				System.out.println(Resultado);		
			}
			indexDeSubeceta =1;
			
			
		}
		
		generarTxt(Resultado);	
		System.out.println(numeroDeRegistros);
		
	}
	
	
	
	public static void generarTxt (String contenido) {
		
		
		 try {
	            String ruta = "C:\\TxtMisRecetas\\Recetas.txt";
	            
	            File file = new File(ruta);
	            // Si el archivo no existe es creado
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            FileWriter fw = new FileWriter(file);
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(contenido);
	            bw.close();
	            System.out.println("ARCHIVO DE RECETAS GENERADO");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	

}
