package com.swingmall.product;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.swingmall.admin.product.ProductVO;
import com.swingmall.cart.Cart;
import com.swingmall.cart.CartVO;
import com.swingmall.main.Page;
import com.swingmall.main.ShopMain;

public class ProductDetail extends Page{

	public JPanel p_content; //상세내용을 담게 될 패널
	public JPanel p_can; //큰 상품이미지 그려질 패널
	JPanel p_option; //옵션 선택 영역
	
	JLabel la_product_name; //상품명 라벨
	JLabel la_price;
	JLabel la_brand;
	
	Choice ch_color; //색상옵션
	Choice ch_size; //사이즈옵션
	
	JButton bt_buy; //구매하기 버튼 
	JButton bt_cart; //장바구니 버튼
	
	private ProductVO vo;
	private Image img;
	
	//상세페이지 호출시 상품1개의 정보는 vo에, 그려질 이미지는 img로 전달받자
	public ProductDetail(ShopMain shopMain) {
		super(shopMain);
		
		p_content = new JPanel();
		p_can = new JPanel() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, p_can);
			}
		};
		
		p_option = new JPanel();
		la_brand = new JLabel();
		la_product_name = new JLabel();
		la_price = new JLabel();
		
		ch_color = new Choice();
		ch_size = new Choice();
		
		ch_color.add("red");
		ch_color.add("pink");
		ch_color.add("black");
		ch_color.add("white");
		ch_color.add("gray");
		
		ch_size.add("S");
		ch_size.add("M");
		ch_size.add("L");
		ch_size.add("XL");
		
		bt_buy = new JButton("구매하기");
		bt_cart = new JButton("장바구니 담기");
		
	
		//스타일 적용 
		p_content.setPreferredSize(new Dimension(ShopMain.WIDTH-100, ShopMain.HEIGHT-200));
				
		Dimension d =new Dimension((ShopMain.WIDTH-100)/3,30);
				
		la_brand.setPreferredSize(d);
		la_product_name.setPreferredSize(d);
		la_price.setPreferredSize(d);
		bt_buy.setPreferredSize(new Dimension(200, 30));
		bt_cart.setPreferredSize(new Dimension(200, 30));
				
		ch_color.setPreferredSize(new Dimension((ShopMain.WIDTH-100)/3,30));
		ch_size.setPreferredSize(new Dimension((ShopMain.WIDTH-100)/3,30));
		
		//조립
		p_content.setLayout(new GridLayout(1,2));
		p_option.add(la_brand);
		p_option.add(la_product_name);
		p_option.add(la_price);
		p_option.add(ch_size);
		p_option.add(ch_color);
		p_option.add(ch_size);
		p_option.add(bt_buy);
		p_option.add(bt_cart);
		
		p_content.add(p_can);
		p_content.add(p_option);
		
		add(p_content);
		
		//장바구니 페이지 열기
		bt_cart.addActionListener((e)->{
			registCart(); //장바구니에 상품 추가하기
			
			//장바구니에 정보가 담겼다고 알려주고, 장바구니 이동 여부를 확인해야 함
			int ans = JOptionPane.showConfirmDialog(ProductDetail.this, "장바구니에 상품이 담겼습니다.\n장바구니로 이동하시겠어요?");
			if(ans == JOptionPane.OK_OPTION) {
				getShopMain().showPage(ShopMain.CART);
			}
			
		});
		
	}
	
	public void init(ProductVO vo,Image img) {
		this.vo = vo; //멤버변수에 현재 보고있는 상품 vo를 주입
		la_brand.setText(vo.getBrand());
		la_product_name.setText(vo.getProduct_name());
		la_price.setText(Integer.toString(vo.getPrice()));
		this.img = img;
		this.img = this.img.getScaledInstance(500, 550, Image.SCALE_SMOOTH);
	}
	
	//장바구니에 등록(DB로 보관하지 않고, 오직 메모리상으로 저장할 예정)
	public void registCart() {
		Cart cartPage= (Cart) getShopMain().getPage()[ShopMain.CART]; //장바구니 페이지에 접근

		CartVO cartVo = new CartVO(); //Empty vo생성
		cartVo.setProduct_id(vo.getProduct_id()); //현재보고있는 상품을 이용하여 CartVO에 채우기
		cartVo.setBrand(vo.getBrand());
		cartVo.setProduct_name(vo.getProduct_name());
		cartVo.setPrice(vo.getPrice());
		cartVo.setFilename(vo.getFilename());
		cartVo.setColor(ch_color.getSelectedItem());
		cartVo.setSize(ch_size.getSelectedItem());
		cartVo.setEa(1); //장바구니에 담을때는 기본이 1개임
		
		cartPage.addCart(cartVo);//장바구니에 상품 1건 추가하기
		cartPage.getCartList(); //장바구니 목록 구성하기
		
	}
	
	
	public ProductVO getVo() {
		return vo;
	}

	public void setVo(ProductVO vo) {
		this.vo = vo;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	
	
	
}
