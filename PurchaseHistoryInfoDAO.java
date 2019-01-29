package com.internousdev.gerbera.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.gerbera.dto.PurchaseHistoryInfoDTO;
import com.internousdev.gerbera.util.DBConnector;

public class PurchaseHistoryInfoDAO {

	public List<PurchaseHistoryInfoDTO>getPurchaseHistoryList(String loginId){
		DBConnector db=new DBConnector();
		Connection con=db.getConnection();
		List<PurchaseHistoryInfoDTO>purchaseHistoryInfoDTOList=new ArrayList<PurchaseHistoryInfoDTO>();
		String sql="select"
				+ " phi.id as id,"
				+ " phi.user_id as user_id,"
				+ " phi.product_count as product_count,"
				+ " pi.product_id as product_id,"
				+ " pi.product_name as product_name,"
				+ " pi.product_name_kana as product_name_kana,"
				+ " pi.product_description as product_description,"
				+ " pi.category_id as category_id,"
				+ " pi.price,"
				+ " pi.image_file_name as image_file_name,"
				+ " pi.image_file_path as image_file_path,"
				+ " pi.release_company,"
				+ " pi.release_date,"
				+ " phi.price as price,"
				+ " phi.regist_date as regist_date,"
				+ " phi.update_date as update_date,"
				+ " di.family_name as family_name,"
				+ " di.first_name as first_name,"
				+ " di.family_name_kana as family_name_kana,"
				+ " di.first_name_kana as first_name_kana,"
				+ " di.email as email,"
				+ " di.tel_number as tel_number,"
				+ " di.user_address as user_address"
				+ " FROM purchase_history_info as phi"
				+ " LEFT JOIN product_info as pi"
				+ " ON phi.product_id = pi.product_id"
				+ " LEFT JOIN destination_info as di"
				+ " ON phi.destination_id = di.id"
				+ " WHERE phi.user_id = ?"
				+ " ORDER BY regist_date DESC";

		try{
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, loginId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				PurchaseHistoryInfoDTO purchaseHistoryInfoDto=new PurchaseHistoryInfoDTO();
				 purchaseHistoryInfoDto.setId(rs.getInt("id"));
				 purchaseHistoryInfoDto.setUserId(rs.getString("user_id"));
				 purchaseHistoryInfoDto.setProductId(rs.getInt("product_id"));
				 purchaseHistoryInfoDto.setProductCount(rs.getInt("product_count"));
				 purchaseHistoryInfoDto.setPrice(rs.getInt("price"));
				 purchaseHistoryInfoDto.setRegistDate(rs.getDate("regist_date"));
				 purchaseHistoryInfoDto.setUpdateDate(rs.getDate("update_date"));
				 purchaseHistoryInfoDto.setProductName(rs.getString("product_name"));
				 purchaseHistoryInfoDto.setProductNameKana(rs.getString("product_name_kana"));
				 purchaseHistoryInfoDto.setProductDescription(rs.getString("product_description"));
				 purchaseHistoryInfoDto.setImageFileName(rs.getString("image_file_name"));
				 purchaseHistoryInfoDto.setImageFilePath(rs.getString("image_file_path"));
				 purchaseHistoryInfoDto.setReleaseCompany(rs.getString("release_company"));
				 purchaseHistoryInfoDto.setReleaseDate(rs.getDate("release_date"));
				 purchaseHistoryInfoDto.setFamilyName(rs.getString("family_name"));
				 purchaseHistoryInfoDto.setFirstName(rs.getString("first_name"));
				 purchaseHistoryInfoDto.setFamilyNameKana(rs.getString("family_name_kana"));
				 purchaseHistoryInfoDto.setFirstNameKana(rs.getString("first_name_kana"));
				 purchaseHistoryInfoDto.setEmail(rs.getString("email"));
				 purchaseHistoryInfoDto.setTelNumber(rs.getString("tel_number"));
				 purchaseHistoryInfoDto.setUserAddress(rs.getString("user_address"));
				 purchaseHistoryInfoDTOList.add(purchaseHistoryInfoDto);

			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return purchaseHistoryInfoDTOList;
	}

	public int regist(String loginId,int productId,int productCount,int destinationId,int price){
		DBConnector db=new DBConnector();
		Connection con=db.getConnection();
		String sql="insert into purchase_history_info(user_id, product_id, product_count, price, destination_id, regist_date, update_date) values (?, ?, ?, ?, ?, now(), '0000-01-01')";
		int count =0;
		try{
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, productId);
			ps.setInt(3, productCount);
			ps.setInt(4, price);
			ps.setInt(5, destinationId);
			count =ps.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}

	public int deleteAll(String loginId){
		DBConnector db=new DBConnector();
		Connection con=db.getConnection();
		String sql="delete from purchase_history_info where user_id=?";
		int count =0;
		try{
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, loginId);
			count=ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}

}
