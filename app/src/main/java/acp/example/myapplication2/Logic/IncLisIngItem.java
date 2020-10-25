package acp.example.myapplication2.Logic;

import android.widget.ImageView;

public class IncLisIngItem {
    private int mintId;
    private String mtxtListQtd;
    private String mtxtLisMed;
    private String mtxtListIng;

    public IncLisIngItem() {
    }

    public IncLisIngItem(int mintId, String mtxtListQtd, String mtxtLisMed, String mtxtListIng ) {
        this.mintId = mintId;
        this.mtxtListQtd = mtxtListQtd;
        this.mtxtLisMed = mtxtLisMed;
        this.mtxtListIng = mtxtListIng;
    }
    public int getMintId() {
        return mintId;
    }

    public void setMintId(int mintId) {
        this.mintId = mintId;
    }

    public String getMtxtListQtd() {
        return mtxtListQtd;
    }

    public void setMtxtListQtd(String mtxtListQtd) {
        this.mtxtListQtd = mtxtListQtd;
    }

    public String getMtxtLisMed() {
        return mtxtLisMed;
    }

    public void setMtxtLisMed(String mtxtLisMed) {
        this.mtxtLisMed = mtxtLisMed;
    }

    public String getMtxtListIng() {
        return mtxtListIng;
    }

    public void setMtxtListIng(String mtxtListIng) {
        this.mtxtListIng = mtxtListIng;
    }

}
