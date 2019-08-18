package com.practice;

import com.myclass.SinglyLinkedListNode;

public class DsAlgoPractice {
	public static void main(String[] args) {
		SinglyLinkedListNode head = new SinglyLinkedListNode();
		head.data = 4;

		SinglyLinkedListNode node1 = new SinglyLinkedListNode();
		node1.data = 5;

		head.next = node1;

		SinglyLinkedListNode node2 = new SinglyLinkedListNode();
		node2.data = 6;

		node1.next = node2;
		
		int result = getNode(head, 1);
		System.out.println(result);

	}
	
	public static int getNode(SinglyLinkedListNode head, int positionFromTail) {
        SinglyLinkedListNode node = head;
        int size = 0;
        
        while (null != node) {
            node = node.next;
            size++;
        }
        node = head;
        for (int i = 0; i < size - (positionFromTail + 1); i++) {
            node = node.next;
        }
        return node.data;
    }

	private static SinglyLinkedListNode mergeLists(SinglyLinkedListNode head1, SinglyLinkedListNode head2) {
		if (null == head1 && null == head2) {
			return null;
		} else if (null == head1) {
			return head2;
		} else if (null == head2) {
			return head1;
		}

		if (head1.data <= head2.data) {
			head1.next = mergeLists(head1.next, head2);
		} else {
			SinglyLinkedListNode temp = head2;
			head2 = head2.next;
			temp.next = head1;
			head1 = temp;
			head1.next = mergeLists(head1.next, head2);
		}

		return head1;
	}
}
