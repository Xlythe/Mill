package com.will.mill;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.DisplayMetrics;
import android.view.View;

public class BoardView extends View{
	private ShapeDrawable[][] mDrawable;
	
	public BoardView(Context context){
		super(context);
		calculateGrid(context);
	}
	
	protected void onDraw(Canvas canvas){
		int n = Global.getN();
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(BoardTools.teamGrid()[i][j]==1){
					mDrawable[i][j].getPaint().setColor(0xffff0000);//Red
				}
				else if(BoardTools.teamGrid()[i][j]==2){
					mDrawable[i][j].getPaint().setColor(0xff00ffff);//Blue
				}
				else if(BoardTools.teamGrid()[i][j]==3){
					mDrawable[i][j].getPaint().setColor(0xffffff00);//Yellow
				}
				else if(BoardTools.teamGrid()[i][j]==4){
					mDrawable[i][j].getPaint().setColor(0xffffff00);//Yellow
				}
				else{
					mDrawable[i][j].getPaint().setColor(0xff74AC23);
				}
				mDrawable[i][j].draw(canvas);
			}
		}
	}
	
	public void calculateGrid(Context context){
		int n = Global.getN();
		mDrawable = new ShapeDrawable[n][n];
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int L=Math.min(metrics.widthPixels/(n+2),metrics.heightPixels/(3*n/2))/2;
		int width = Math.min(metrics.widthPixels/(n+2),metrics.heightPixels/(3*n/2));
		int height = Math.min(metrics.widthPixels/(n+2),metrics.heightPixels/(3*n/2));
		Global.setHexLength(L);
		//double game_length=L*Math.sqrt(3) * n * (n-1)*L*(Math.sqrt(3)/2);
		int T = 3;
		
		int x=width;
		int y=height;
		int spacing=0;
		
		//Shape of a corner (top right)
		Path corner_tr_path = new Path();
		corner_tr_path.moveTo(0, 0+L/2);
		corner_tr_path.lineTo(0, L+L/2);
		corner_tr_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		corner_tr_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		corner_tr_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		corner_tr_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		corner_tr_path.close();
		
		//Shape of a corner (top left)
		Path corner_tl_path = new Path();
		corner_tl_path.moveTo(0, 0+L/2);
		corner_tl_path.lineTo(0, L+L/2);
		corner_tl_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		corner_tl_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		corner_tl_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		corner_tl_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		corner_tl_path.close();
		
		//Shape of a corner (bottom right)
		Path corner_br_path = new Path();
		corner_br_path.moveTo(0, 0+L/2);
		corner_br_path.lineTo(0, L+L/2);
		corner_br_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		corner_br_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		corner_br_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		corner_br_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		corner_br_path.close();
		
		//Shape of a corner (bottom left)
		Path corner_bl_path = new Path();
		corner_bl_path.moveTo(0, 0+L/2);
		corner_bl_path.lineTo(0, L+L/2);
		corner_bl_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		corner_bl_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		corner_bl_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		corner_bl_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		corner_bl_path.close();
		
		//Shape of a T-cross (Right)
		Path Tcross_r_path = new Path();
		Tcross_r_path.moveTo(0, 0+L/2);
		Tcross_r_path.lineTo(0, L+L/2);
		Tcross_r_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		Tcross_r_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		Tcross_r_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		Tcross_r_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		Tcross_r_path.close();
		
		//Shape of a T-cross (Left)
		Path Tcross_l_path = new Path();
		Tcross_l_path.moveTo(0, 0+L/2);
		Tcross_l_path.lineTo(0, L+L/2);
		Tcross_l_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		Tcross_l_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		Tcross_l_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		Tcross_l_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		Tcross_l_path.close();
		
		//Shape of a T-cross (Up)
		Path Tcross_u_path = new Path();
		Tcross_u_path.moveTo(0, 0+L/2);
		Tcross_u_path.lineTo(0, L+L/2);
		Tcross_u_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		Tcross_u_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		Tcross_u_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		Tcross_u_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		Tcross_u_path.close();
		
		//Shape of a T-cross (Down)
		Path Tcross_d_path = new Path();
		Tcross_d_path.moveTo(0, 0+L/2);
		Tcross_d_path.lineTo(0, L+L/2);
		Tcross_d_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		Tcross_d_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		Tcross_d_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		Tcross_d_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		Tcross_d_path.close();
        
		//Shape of a cross
		Path cross_path = new Path();
		cross_path.moveTo(0, 0+L/2);
		cross_path.lineTo(0, L+L/2);
		cross_path.lineTo((float) (-L*Math.sqrt(3)/2),L+L*1/2+L/2);
		cross_path.lineTo((float) (-L*Math.sqrt(3)),L+L/2);
		cross_path.lineTo((float) (-L*Math.sqrt(3)),0+L/2);
		cross_path.lineTo((float) (-L*Math.sqrt(3)/2),-L*1/2+L/2);
		cross_path.close();
				
		//Shape of a line (vertical)
		Path line_vert_path = new Path();
		line_vert_path.moveTo(-(L-T), 0);
		line_vert_path.lineTo(-(L-T), 2*L);
		line_vert_path.lineTo(-(L+T), 2*L);
		line_vert_path.lineTo(-(L+T), 0);
		line_vert_path.close();
		
		//Shape of a line (horizontal)
		Path line_horz_path = new Path();
		line_horz_path.moveTo(0, L-T);
		line_horz_path.lineTo(-2*L, L-T);
		line_horz_path.lineTo(-2*L, L+T);
		line_horz_path.lineTo(0, L+T);
		line_horz_path.close();
		
		//Shape of a block
		Path block_path = new Path();
		block_path.moveTo(0, 0);
		block_path.lineTo(2*L, 0);
		block_path.lineTo(2*L, 2*L);
		block_path.lineTo(0, 2*L);
		block_path.close();
		
		//Shape of a blank
		Path blank_path = new Path();
		blank_path.moveTo(0, 0);
		blank_path.close();
		
		x=2*width+spacing;
		
		mDrawable[0][0] = new ShapeDrawable(new PathShape(corner_tl_path, width, height));
		mDrawable[0][0].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(0, 0, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[0][1] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[0][1].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[0][2] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[0][2].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[0][3] = new ShapeDrawable(new PathShape(Tcross_d_path, width, height));
		mDrawable[0][3].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(0, 3, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[0][4] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[0][4].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[0][5] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[0][5].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[0][6] = new ShapeDrawable(new PathShape(corner_tr_path, width, height));
		mDrawable[0][6].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(0, 6, new Posn(x-2*L,y));
		x+=width;
		
		//---------------------------------------------------------
		x=2*width+spacing;
		y+=height;
		
		mDrawable[1][0] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[1][0].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[1][1] = new ShapeDrawable(new PathShape(corner_tl_path, width, height));
		mDrawable[1][1].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(1, 1, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[1][2] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[1][2].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[1][3] = new ShapeDrawable(new PathShape(cross_path, width, height));
		mDrawable[1][3].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(1, 3, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[1][4] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[1][4].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[1][5] = new ShapeDrawable(new PathShape(corner_tr_path, width, height));
		mDrawable[1][5].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(1, 5, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[1][6] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[1][6].setBounds(x,y,x+width,y+height);
		x+=width;
		
		//---------------------------------------------------------
		x=2*width+spacing;
		y+=height;
		
		mDrawable[2][0] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[2][0].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[2][1] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[2][1].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[2][2] = new ShapeDrawable(new PathShape(corner_tl_path, width, height));
		mDrawable[2][2].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(2, 2, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[2][3] = new ShapeDrawable(new PathShape(Tcross_u_path, width, height));
		mDrawable[2][3].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(2, 3, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[2][4] = new ShapeDrawable(new PathShape(corner_tr_path, width, height));
		mDrawable[2][4].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(2, 4, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[2][5] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[2][5].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[2][6] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[2][6].setBounds(x,y,x+width,y+height);
		x+=width;
		
		//---------------------------------------------------------
		x=2*width+spacing;
		y+=height;
		
		mDrawable[3][0] = new ShapeDrawable(new PathShape(Tcross_r_path, width, height));
		mDrawable[3][0].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(3, 0, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[3][1] = new ShapeDrawable(new PathShape(cross_path, width, height));
		mDrawable[3][1].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(3, 1, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[3][2] = new ShapeDrawable(new PathShape(Tcross_l_path, width, height));
		mDrawable[3][2].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(3, 2, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[3][3] = new ShapeDrawable(new PathShape(blank_path, width, height));
		mDrawable[3][3].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[3][4] = new ShapeDrawable(new PathShape(Tcross_r_path, width, height));
		mDrawable[3][4].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(3, 4, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[3][5] = new ShapeDrawable(new PathShape(cross_path, width, height));
		mDrawable[3][5].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(3, 5, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[3][6] = new ShapeDrawable(new PathShape(Tcross_l_path, width, height));
		mDrawable[3][6].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(3, 6, new Posn(x-2*L,y));
		x+=width;
		
		//---------------------------------------------------------
		x=2*width+spacing;
		y+=height;
		
		mDrawable[4][0] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[4][0].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[4][1] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[4][1].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[4][2] = new ShapeDrawable(new PathShape(corner_bl_path, width, height));
		mDrawable[4][2].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(4, 2, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[4][3] = new ShapeDrawable(new PathShape(Tcross_d_path, width, height));
		mDrawable[4][3].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(4, 3, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[4][4] = new ShapeDrawable(new PathShape(corner_br_path, width, height));
		mDrawable[4][4].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(4, 4, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[4][5] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[4][5].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[4][6] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[4][6].setBounds(x,y,x+width,y+height);
		x+=width;
		
		//---------------------------------------------------------
		x=2*width+spacing;
		y+=height;
		
		mDrawable[5][0] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[5][0].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[5][1] = new ShapeDrawable(new PathShape(corner_bl_path, width, height));
		mDrawable[5][1].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(5, 1, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[5][2] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[5][2].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[5][3] = new ShapeDrawable(new PathShape(cross_path, width, height));
		mDrawable[5][3].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(5, 3, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[5][4] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[5][4].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[5][5] = new ShapeDrawable(new PathShape(corner_br_path, width, height));
		mDrawable[5][5].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(5, 5, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[5][6] = new ShapeDrawable(new PathShape(line_vert_path, width, height));
		mDrawable[5][6].setBounds(x,y,x+width,y+height);
		x+=width;
		
		//---------------------------------------------------------
		x=2*width+spacing;
		y+=height;
		
		mDrawable[6][0] = new ShapeDrawable(new PathShape(corner_bl_path, width, height));
		mDrawable[6][0].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(6, 0, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[6][1] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[6][1].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[6][2] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[6][2].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[6][3] = new ShapeDrawable(new PathShape(Tcross_u_path, width, height));
		mDrawable[6][3].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(6, 3, new Posn(x-2*L,y));
		x+=width;
		
		mDrawable[6][4] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[6][4].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[6][5] = new ShapeDrawable(new PathShape(line_horz_path, width, height));
		mDrawable[6][5].setBounds(x,y,x+width,y+height);
		x+=width;
		
		mDrawable[6][6] = new ShapeDrawable(new PathShape(corner_br_path, width, height));
		mDrawable[6][6].setBounds(x,y,x+width,y+height);
		BoardTools.setPolyXY(6, 6, new Posn(x-2*L,y));
		x+=width;
	}
}