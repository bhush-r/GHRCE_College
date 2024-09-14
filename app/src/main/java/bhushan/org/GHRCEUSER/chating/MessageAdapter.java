//package bhushan.org.GHRCEUSER.chating;
//
//import android.os.Message;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.text.SimpleDateFormat;
//import bhushan.org.GHRCEUSER.R;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
//
//    private List<Message> messageList;
//    private String currentUserId;
//
//    public MessageAdapter(List<Message> messageList, String currentUserId) {
//        this.messageList = messageList;
//        this.currentUserId = currentUserId;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Message message = messageList.get(position);
//
//        if (message.getSenderId().equals(currentUserId)) {
//            holder.senderNameTextView.setVisibility(View.GONE);
//            holder.chatContainer.setVisibility(View.GONE);
//            holder.chatContainerRight.setVisibility(View.VISIBLE);
//            holder.chatTextViewRight.setText(message.getMessageText());
//            holder.timestampTextViewRight.setText(getFormattedTime(message.getTimestamp()));
//        } else {
//            holder.senderNameTextView.setVisibility(View.VISIBLE);
//            holder.chatContainer.setVisibility(View.VISIBLE);
//            holder.chatContainerRight.setVisibility(View.GONE);
//            holder.senderNameTextView.setText(message.getSenderName());
//            holder.chatTextView.setText(message.getMessageText());
//            holder.timestampTextView.setText(getFormattedTime(message.getTimestamp()));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return messageList.size();
//    }
//
//    private String getFormattedTime(long timestamp) {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        return sdf.format(new Date(timestamp));
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView senderNameTextView;
//        LinearLayout chatContainer;
//        LinearLayout chatContainerRight;
//        TextView chatTextView;
//        TextView chatTextViewRight;
//        TextView timestampTextView;
//        TextView timestampTextViewRight;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            senderNameTextView = itemView.findViewById(R.id.sender_name_text_view);
//            chatContainer = itemView.findViewById(R.id.chat_container);
//            chatContainerRight = itemView.findViewById(R.id.chat_container_right);
//            chatTextView = itemView.findViewById(R.id.chat_text_view);
//            chatTextViewRight = itemView.findViewById(R.id.chat_text_view_right);
//            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
//            timestampTextViewRight = itemView.findViewById(R.id.timestamp_text_view_right);
//        }
//    }
//
//}
//
